package com.orange.bookstore.config;

import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.FieldError;

public class FieldErrorsExtractor {
	
	private Map<String, String> fieldErrors = new HashMap<>();
	
	public FieldErrorsExtractor(List<FieldError> errors) {
		this.fieldErrors = errors.stream().collect(toMap(FieldError::getField, FieldError::getDefaultMessage));
	}

	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}
}
