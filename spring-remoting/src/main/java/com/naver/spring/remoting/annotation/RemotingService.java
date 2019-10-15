package com.naver.spring.remoting.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 이 annotation 을 추가한 객체는 Component bean 으로 생성되며, {@code HttpInvokerServiceExporter} 를 통해
 * {@code HttpInvokerProxyFactoryBean} 를 통한 원격 호출이 가능해 진다.
 *
 * @author yongkyu.lee
 * @since 0.1.0
 */
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemotingService {

	@AliasFor(annotation = Component.class)
	String value() default "";

	/**
	 * (optional} 원격 호출하기 위한 service 는 반드시 원격 호출에 사용될 interface 를 구현해야 하며
	 * 이 attribute 를 통해 interface 를 명시할 수 있다. 명시하지 않은 경우 이 객체가 구현한 interface 들중 첫번째 것으로
	 * 원격 노출한다.
	 */
	Class<?> serviceInterface() default Object.class;
}
