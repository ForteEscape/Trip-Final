package com.user.util;

import org.springframework.stereotype.Component;

@Component
public class UserCodeGenerator {
	
	private static final int MOD = 100_000_007;
	private static final int MAX = 1_000_000_000;
	private static final String PREFIX = "#";
	
	private final int MULTIPLIER = 31;
	
	public String generateUserCode(String userEmail) {
		long hash = 7L;
		int emailLength = userEmail.length();
		
		for(int i = 0; i < emailLength; i++) {
			hash = MULTIPLIER * hash + userEmail.charAt(i);
		}
		
		if(hash >= MAX) {
			hash %= MOD;
		}
		
		return PREFIX + hash;
	}
}