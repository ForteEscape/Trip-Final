package com.common.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class Helper {
	
	public static LinkedList<LinkedHashMap<String, String>> refineErrors(Errors errors) {
		LinkedList<LinkedHashMap<String, String>> errorList = new LinkedList<>();
		
		errors.getFieldErrors().forEach(e -> {
			LinkedHashMap<String, String> error = new LinkedHashMap<>();
			error.put("field", e.getField());
			error.put("message", e.getDefaultMessage());
			errorList.add(error);
		});
		
		return errorList;
	}
}
