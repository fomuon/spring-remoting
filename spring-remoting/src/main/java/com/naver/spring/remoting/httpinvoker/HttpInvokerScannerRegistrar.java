package com.naver.spring.remoting.httpinvoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * {@link HttpInvokerScan} 에 정의된 정보를 통해 원격 서비스 interface 를 스캔하여 HttpInvokerProxyFactoryBean 로 등록해 준다.
 *
 * @author yongkyu.lee
 * @since 0.1.0
 */
public class HttpInvokerScannerRegistrar implements ImportBeanDefinitionRegistrar {
	private final Logger log = LoggerFactory.getLogger(HttpInvokerScannerRegistrar.class);

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		AnnotationAttributes attrs = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(HttpInvokerScan.class.getName()));

		if (attrs != null) {
			scanAndRegisterHttpInvoker(attrs, registry);
		}
	}

	private void scanAndRegisterHttpInvoker(AnnotationAttributes attrs, BeanDefinitionRegistry registry) {
		String baseUrl = attrs.getString("baseUrl");
		String[] basePackages = attrs.getStringArray("basePackages");
		String httpInvokerRequestExecutorRef = attrs.getString("httpInvokerRequestExecutorRef");

		Assert.hasText(baseUrl, "baseUrl is required");
		Assert.notEmpty(basePackages, "basePackages must not be empty");

		ClassPathHttpInvokerScanner scanner = new ClassPathHttpInvokerScanner(registry);
		List<GenericBeanDefinition> beanDefinitions = scanner.scanCandidates(attrs.getStringArray("basePackages"));

		for (GenericBeanDefinition definition : beanDefinitions) {
			String serviceInterface = definition.getBeanClassName();
			String serviceUrl = baseUrl + serviceInterface;

			definition.getPropertyValues().addPropertyValue("serviceUrl", serviceUrl);
			definition.getPropertyValues().addPropertyValue("serviceInterface",  serviceInterface);

			if (!StringUtils.isEmpty(httpInvokerRequestExecutorRef)) {
				definition.getPropertyValues().add("httpInvokerRequestExecutor",
						new RuntimeBeanReference(httpInvokerRequestExecutorRef));
			}

			definition.setBeanClass(HttpInvokerProxyFactoryBean.class);

			if (log.isDebugEnabled()) {
				log.debug("'{}' will be registered as HttpInvokerProxyFactoryBean", serviceInterface);
			}
		}
	}
}
