package com.common.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.util.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * JWT 인증을 위해 설치하는 Filter
 * UsernamePasswordAuthenticationFilter 이전에 실행
 * 
 * UsernamePasswordAuthenticationFilter 는 email(username), password를 사용하여 username에 해당하는
 * 사람이 저장소 내부에 존재하는지를 확인하고, 존재할 경우, password를 비교하여 일치하는지를 확인한다.
 */

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final JwtTokenProvider tokenProvider;
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = resolveToken(request);
		log.info("expired : " + tokenProvider.validateToken(token));
		if(token != null && tokenProvider.validateToken(token)) {
			Authentication authentication = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else if(token != null && !tokenProvider.validateToken(token)){
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT Expired");
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7);
		}
		
		return null;
	}
	
}
