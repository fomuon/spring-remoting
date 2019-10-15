package com.naver.spring.remoting.example.provider.config;

import com.naver.spring.remoting.httpinvoker.HttpInvokerRemotingServiceConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpInvokerRemotingServiceConfigurer.class)
public class ProviderConfig {
}
