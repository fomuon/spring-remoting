package com.naver.spring.remoting.httpinvoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassPathHttpInvokerScanner extends ClassPathBeanDefinitionScanner {
	private final Logger log = LoggerFactory.getLogger(ClassPathHttpInvokerScanner.class);

	public ClassPathHttpInvokerScanner(BeanDefinitionRegistry registry) {
		super(registry, false);

		addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	}

	public List<GenericBeanDefinition> scanServices(String[] basePackages) {
		Set<BeanDefinitionHolder> definitionHolders = super.doScan(basePackages);

		List<GenericBeanDefinition> beanDefinitions = new ArrayList<>();

		for (BeanDefinitionHolder definitionHolder : definitionHolders) {
			beanDefinitions.add((GenericBeanDefinition)definitionHolder.getBeanDefinition());
		}

		return beanDefinitions;
	}
}
