package com.naver.spring.remoting.example.consumer.config;

import com.naver.spring.remoting.httpinvoker.HttpInvokerScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;

@Configuration
@HttpInvokerScan(baseUrl = "http://localhost:8081/",
		basePackages = "com.naver.spring.remoting.example.core",
		httpInvokerRequestExecutorRef = "httpInvokerRequestExecutor")
public class ConsumerConfig {

	@Bean
	public HttpInvokerRequestExecutor httpInvokerRequestExecutor() {
		HttpComponentsHttpInvokerRequestExecutor requestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
		requestExecutor.setConnectTimeout(20950);

		return requestExecutor;

	}
}
