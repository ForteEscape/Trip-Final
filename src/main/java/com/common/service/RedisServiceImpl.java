package com.common.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
	
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void setValue(String key, String value) {
		// TODO Auto-generated method stub
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, values);
	}

	@Override
	public void setValues(String key, String value, Duration duration) {
		// TODO Auto-generated method stub
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, values, duration);
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		Object result = values.get(key);
		
		if(result == null) {
			return "";
		}
		
		return String.valueOf(result);
	}

	@Override
	public void deleteValue(String key) {
		// TODO Auto-generated method stub
		redisTemplate.delete(key);
	}

}
