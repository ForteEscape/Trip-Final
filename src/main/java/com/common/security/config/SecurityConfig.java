package com.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.util.JwtTokenProvider;
import com.common.security.component.JwtAccessDeniedHandler;
import com.common.security.component.JwtAuthenticationEntryPoint;
import com.common.security.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = { "/api/v1/member/**", "/swagger-ui/**", "/api-docs",
			"/swagger-ui-customer.html", "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/auth/**" };
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable());
		http.cors(Customizer.withDefaults());

		// session 관리 상태를 stateless로 구성
		http.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.formLogin((form) -> form.disable());
		http.httpBasic(AbstractHttpConfigurer::disable);
		
		http.exceptionHandling((exceptionHandling) -> exceptionHandling
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
		);
		
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(AUTH_WHITELIST)
				.permitAll()
				.anyRequest()
				.permitAll()
		);
		
		http.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		

		return http.build();
	}
}
