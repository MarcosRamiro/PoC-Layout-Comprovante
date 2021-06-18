package com.ramiro.poclayoutcomprovante.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ramiro.binder.ServiceBind;

@Configuration
public class AppConfig {
	
	@Bean
	public ServiceBind serviceBind() {
		return new ServiceBind();
	}

}
