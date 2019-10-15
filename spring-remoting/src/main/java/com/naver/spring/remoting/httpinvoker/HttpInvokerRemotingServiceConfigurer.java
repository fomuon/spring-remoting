package com.naver.spring.remoting.httpinvoker;

import com.naver.spring.remoting.annotation.RemotingService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@code RemotingService} annotation 을 추가한 객체를 Component 로 등록하고,
 * 원격 호출 될 수 있도록 {@code HttpInvokerServiceExporter} 를 생성한다.
 *
 * @author yongkyu.lee
 * @since 0.1.0
 */
public class HttpInvokerRemotingServiceConfigurer implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		for (RemotingServiceBeanMetadata beanMetadata : getBeanDefinitionWithRemotingService(beanFactory)) {
			String beanName = beanMetadata.getBeanName();
			AnnotationMetadata metadata = beanMetadata.getBeanDefinition().getMetadata();

			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RemotingService.class.getName());
			Class<?> serviceInterface = (Class<?>)annotationAttributes.get("serviceInterface");

			if (serviceInterface == Object.class) {
				try {
					serviceInterface = Class.forName(metadata.getInterfaceNames()[0]);
				} catch (ClassNotFoundException ignored) {
				}
			}

			if (!Arrays.asList(metadata.getInterfaceNames()).contains(serviceInterface.getName())) {
				throw new BeanDefinitionValidationException("'" + metadata.getClassName()
						+ "' did not implement '" + serviceInterface.getName() + "'");
			}

			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HttpInvokerServiceExporter.class)
					.addPropertyReference("service", beanName)
					.addPropertyValue("serviceInterface", serviceInterface);

			((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("/" + serviceInterface.getName(), builder.getBeanDefinition());
		}
	}

	private List<RemotingServiceBeanMetadata> getBeanDefinitionWithRemotingService(ConfigurableListableBeanFactory beanFactory) {
		List<RemotingServiceBeanMetadata> beanDefinitions = new ArrayList<>();

		for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

			if (beanDefinition instanceof ScannedGenericBeanDefinition) {
				AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) beanDefinition).getMetadata();

				if (metadata.getAnnotationTypes().contains(RemotingService.class.getName())) {
					beanDefinitions.add(new RemotingServiceBeanMetadata(beanDefinitionName, (ScannedGenericBeanDefinition)beanDefinition));
				}
			}
		}

		return beanDefinitions;
	}

	private static class RemotingServiceBeanMetadata {
		private String beanName;
		private ScannedGenericBeanDefinition beanDefinition;

		RemotingServiceBeanMetadata(String beanName, ScannedGenericBeanDefinition beanDefinition) {
			this.beanName = beanName;
			this.beanDefinition = beanDefinition;
		}

		String getBeanName() {
			return beanName;
		}

		ScannedGenericBeanDefinition getBeanDefinition() {
			return beanDefinition;
		}
	}
}
