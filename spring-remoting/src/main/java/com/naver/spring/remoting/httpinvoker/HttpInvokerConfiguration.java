package com.naver.spring.remoting.httpinvoker;

import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class HttpInvokerConfiguration {

	private List<ConfigurationHold> configurationHolds = new ArrayList<>();

	public void addHttpInvokerConfiguration(String baseUrl, String...basePackages) {
		this.addHttpInvokerConfiguration(baseUrl, null, basePackages);
	}

	public void addHttpInvokerConfiguration(String baseUrl, HttpInvokerRequestExecutor requestExecutor, String...basePackages) {
		Assert.hasText(baseUrl, "baseUrl is required");
		Assert.notEmpty(basePackages, "basePackages must not be empty");

		configurationHolds.add(new ConfigurationHold(baseUrl, basePackages, null, requestExecutor));
	}

	public void addHttpInvokerConfiguration(String baseUrl, Class<?>...serviceInterfaces) {
		this.addHttpInvokerConfiguration(baseUrl, null, serviceInterfaces);
	}

	public void addHttpInvokerConfiguration(String baseUrl, HttpInvokerRequestExecutor requestExecutor, Class<?>...serviceInterfaces) {
		Assert.hasText(baseUrl, "baseUrl is required");
		Assert.notEmpty(serviceInterfaces, "serviceInterfaces must not be empty");

		configurationHolds.add(new ConfigurationHold(baseUrl, null, serviceInterfaces, requestExecutor));
	}

	List<ConfigurationHold> getConfigurationHolds() {
		return configurationHolds;
	}

	static class ConfigurationHold {
		private final String baseUrl;
		private final String[] basePackages;
		private final Class<?>[] serviceInterfaces;
		private final HttpInvokerRequestExecutor requestExecutor;

		public ConfigurationHold(String baseUrl, String[] basePackages, Class<?>[] serviceInterfaces,
				HttpInvokerRequestExecutor requestExecutor) {
			this.baseUrl = baseUrl;
			this.basePackages = basePackages;
			this.serviceInterfaces = serviceInterfaces;
			this.requestExecutor = requestExecutor;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public String[] getBasePackages() {
			return basePackages;
		}

		public Class<?>[] getServiceInterfaces() {
			return serviceInterfaces;
		}

		public HttpInvokerRequestExecutor getRequestExecutor() {
			return requestExecutor;
		}
	}
}
