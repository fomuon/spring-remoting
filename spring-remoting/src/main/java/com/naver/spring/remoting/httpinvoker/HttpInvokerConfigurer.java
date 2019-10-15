package com.naver.spring.remoting.httpinvoker;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

public abstract class HttpInvokerConfigurer implements BeanDefinitionRegistryPostProcessor {

	private HttpInvokerConfiguration httpInvokerConfiguration = new HttpInvokerConfiguration();

	protected abstract void configureHttpInvoker(HttpInvokerConfiguration httpInvokerConfiguration);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// left intentionally blank
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

		configureHttpInvoker(httpInvokerConfiguration);

		Assert.notEmpty(httpInvokerConfiguration.getConfigurationHolds(), "httpInvokerConfiguration must not be empty");

		for (HttpInvokerConfiguration.ConfigurationHold configurationHold : httpInvokerConfiguration.getConfigurationHolds()) {

			if (configurationHold.getBasePackages() != null) {
				scanAndRegister(registry, configurationHold.getBaseUrl(), configurationHold.getBasePackages(),
						configurationHold.getRequestExecutor());
			}

			if (configurationHold.getServiceInterfaces() != null) {
				registerWithServiceInterfaces(registry, configurationHold.getBaseUrl(),
						configurationHold.getServiceInterfaces(), configurationHold.getRequestExecutor());
			}
		}
	}

	private void scanAndRegister(BeanDefinitionRegistry registry, String baseUrl, String[] basePackages,
			HttpInvokerRequestExecutor requestExecutor) {

		ClassPathHttpInvokerScanner scanner = new ClassPathHttpInvokerScanner(registry);

		List<GenericBeanDefinition> beanDefinitions = scanner.scanServices(basePackages);

		for (GenericBeanDefinition definition : beanDefinitions) {
			definition.getPropertyValues().addPropertyValue("serviceUrl", baseUrl + definition.getBeanClassName());
			definition.getPropertyValues().addPropertyValue("serviceInterface",  definition.getBeanClassName());

			if (requestExecutor != null) {
				definition.getPropertyValues().addPropertyValue("httpInvokerRequestExecutor", requestExecutor);
			}

			definition.setBeanClass(HttpInvokerProxyFactoryBean.class);
		}
	}

	private void registerWithServiceInterfaces(BeanDefinitionRegistry registry, String baseUrl,
			Class<?>[] serviceInterfaces, HttpInvokerRequestExecutor requestExecutor) {

		for (Class<?> serviceInterface : serviceInterfaces) {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HttpInvokerProxyFactoryBean.class)
					.addPropertyValue("serviceUrl", baseUrl + serviceInterface.getName())
					.addPropertyValue("serviceInterface", serviceInterface.getName());

			if (requestExecutor != null) {
				builder.addPropertyValue("httpInvokerRequestExecutor", requestExecutor);
			}

			registry.registerBeanDefinition(StringUtils.uncapitalize(serviceInterface.getSimpleName()),
					builder.getBeanDefinition());
		}
	}
}
