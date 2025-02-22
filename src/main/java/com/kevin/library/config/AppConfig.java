package com.kevin.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kevin.library.interceptor.JwtInterceptor;

import io.micrometer.common.lang.NonNull;

@Configuration
public class AppConfig implements WebMvcConfigurer{
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Value("${cors.allowed.origins}") // 從設定檔讀取 CORS 允許的來源
	private String allowedOrigins;
	
	@Bean
	PasswordEncoder passwordEncoder() { // 加密輸入的密碼
		return new BCryptPasswordEncoder();
	}
			@Override
			public void addInterceptors(@NonNull InterceptorRegistry registry) { 
				
				registry.addInterceptor(jwtInterceptor) //添加攔截器
				.addPathPatterns("/book/showBorrowedBook")//添加攔截路徑
				.addPathPatterns("/book/borrowABook")
				.addPathPatterns("/book/returnABook");
			}
	
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // 匹配所有的 API 路徑
						.allowedOrigins(allowedOrigins.split(",")) // 必須設置為具體的前端地址
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的 HTTP 方法
						.allowCredentials(true) // 允許攜帶 Cookie
						.allowedHeaders("*") // 允許所有的 Header
						.exposedHeaders("Authorization");
			
		}


}
