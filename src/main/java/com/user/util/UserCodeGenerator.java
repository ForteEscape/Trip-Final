package com.user.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserCodeGenerator {
	
	private static final int MOD = 100_000_007;
	private static final int MAX = 1_000_000_000;
	private static final String PREFIX = "#";
	
	private final int MULTIPLIER = 31;
	
	public String generateUserCode(String userEmail) {
		long hash = 7L;
		int emailLength = userEmail.length();
		
		for(int i = 0; i < emailLength; i++) {
			hash = (MULTIPLIER * hash) % MOD + userEmail.charAt(i);
		}
		
		if(hash >= MAX) {
			hash %= MOD;
		}
		
		log.info("userCode : " + PREFIX + hash);
		log.info("length : " + String.valueOf(PREFIX + hash).length());
		
		return PREFIX + hash;
	}
}