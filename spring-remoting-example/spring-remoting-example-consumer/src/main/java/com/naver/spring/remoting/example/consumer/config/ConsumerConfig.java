package com.naver.spring.remoting.example.consumer.config;

import com.naver.spring.remoting.example.core.shoping.OrderGoodsService;
import com.naver.spring.remoting.httpinvoker.HttpInvokerConfiguration;
import com.naver.spring.remoting.httpinvoker.HttpInvokerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;

@Configuration
public class ConsumerConfig extends HttpInvokerConfigurer {
	private HttpComponentsHttpInvokerRequestExecutor requestExecutor;

	public ConsumerConfig() {
		requestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
	}

	@Override
	public void configureHttpInvoker(HttpInvokerConfiguration httpInvokerConfiguration) {

		httpInvokerConfiguration.addHttpInvokerConfiguration("http://localhost:8081/",
				requestExecutor,
				"com.naver.spring.remoting.example.core.booking");

		httpInvokerConfiguration.addHttpInvokerConfiguration("http://localhost:8081/",
				requestExecutor,
				OrderGoodsService.class);
	}
}
