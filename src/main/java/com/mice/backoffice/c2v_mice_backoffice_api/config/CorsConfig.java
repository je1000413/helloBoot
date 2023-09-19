package com.mice.backoffice.c2v_mice_backoffice_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Spring Security 에서 사용할 CORS Filter 설정
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Configuration
public class CorsConfig {
	@Value("${allow.origin.pattern}")
	private List<String> allowOriginPattern;
	
	@Bean
	protected CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(allowOriginPattern);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
