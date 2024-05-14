package com.user.service;

import com.auth.vo.Token;
import com.user.vo.LoginRequest;

public interface UserService {
	
	Token login(LoginRequest request);
}
