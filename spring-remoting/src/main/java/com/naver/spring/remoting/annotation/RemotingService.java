package com.naver.spring.remoting.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RemotingService {

	@AliasFor(annotation = Component.class)
	String value() default "";

	Class<?> serviceInterface() default Object.class;
}
