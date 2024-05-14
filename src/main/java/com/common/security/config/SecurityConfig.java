package com.common.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = { "/api/v1/member/**", "/swagger-ui/**", "/api-docs",
			"/swagger-ui-customer.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**" };

	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable());
		http.cors(Customizer.withDefaults());

		// session 관리 상태를 stateless로 구성
		http.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.formLogin((form) -> form.disable());
		http.httpBasic(AbstractHttpConfigurer::disable);
		
		http.exceptionHandling((exceptionHandling) -> exceptionHandling
				.authenticationEntryPoint(null)
				.accessDeniedHandler(null)
		);
		
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(AUTH_WHITELIST)
				.permitAll()
				.anyRequest()
				.permitAll()
		);
		
		http.addFilterBefore(null, null);
		

		return http.build();
	}
}
