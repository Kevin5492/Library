package com.kevin.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kevin.library.interceptor.JwtInterceptor;

import io.micrometer.common.lang.NonNull;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
@Bean
PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}
@Override
public void addInterceptors(@NonNull InterceptorRegistry registry) {
	
	registry.addInterceptor(jwtInterceptor).addPathPatterns("/book/borrow");
}
}
