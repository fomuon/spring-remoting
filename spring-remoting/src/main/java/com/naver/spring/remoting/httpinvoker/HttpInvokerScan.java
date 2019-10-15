package com.naver.spring.remoting.httpinvoker;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Java Config 를 사용할때 이 annotation 을 사용하여 RemotingService interface 를 HttpInvokerProxy 로 등록한다.
 *
 * @author yongkyu.lee
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HttpInvokerScannerRegistrar.class)
public @interface HttpInvokerScan {

	/**
	 * Http 원격 서비스가 위치하는 baseUrl
	 */
	String baseUrl();

	/**
	 * 원격 서비스 interface 를 scan 하기 위한 package 목록
	 */
	String[] basePackages();

	/**
	 * (optional) {@code HttpInvokerRequestExecutor} bean name 명시
	 * 기본값 {@link org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor}
	 * @return
	 */
	String httpInvokerRequestExecutorRef() default "";
}
