package com.naver.spring.remoting.httpinvoker;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 지정된 package 내의 interface 를 스캔 하여 GenericBeanDefinition 형태로 반환한다.
 *
 * @author yongkyu.lee
 * @since 0.1.0
 */
public class ClassPathHttpInvokerScanner extends ClassPathBeanDefinitionScanner {

	public ClassPathHttpInvokerScanner(BeanDefinitionRegistry registry) {
		super(registry, false);

		addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	}

	public List<GenericBeanDefinition> scanCandidates(String[] basePackages) {
		Set<BeanDefinitionHolder> definitionHolders = super.doScan(basePackages);

		List<GenericBeanDefinition> beanDefinitions = new ArrayList<>();

		for (BeanDefinitionHolder definitionHolder : definitionHolders) {
			beanDefinitions.add((GenericBeanDefinition)definitionHolder.getBeanDefinition());
		}

		return beanDefinitions;
	}
}
