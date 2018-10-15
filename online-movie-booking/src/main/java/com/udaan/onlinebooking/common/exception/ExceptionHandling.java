package com.udaan.onlinebooking.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.udaan.onlinebooking.common.messages.Messages;

@RestControllerAdvice
public class ExceptionHandling {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Map<String,Object> handleError(DataIntegrityViolationException e) {
		Map<String,Object> errorResponse = new HashMap<>();
		errorResponse.put("code", "C001");
		errorResponse.put("message", "Data Provided isn't compatible,Not Able to Persist!");
		return errorResponse;
	}
	
	@ExceptionHandler(InvalidAisleValuesException.class)
	public Map<String,Object> handleError(InvalidAisleValuesException e) {
		Map<String,Object> errorResponse = new HashMap<>();
		errorResponse.put("code", Messages.InvalidAisleValuesException.getCode());
		errorResponse.put("message", e.getMessage());
		return errorResponse;
	}

	@ExceptionHandler(InvalidBookingException.class)
	public Map<String,Object> handleError(InvalidBookingException e) {
		Map<String,Object> errorResponse = new HashMap<>();
		errorResponse.put("code", Messages.InvalidBookingException.getCode());
		errorResponse.put("message", e.getMessage());
		return errorResponse;
	}
}
