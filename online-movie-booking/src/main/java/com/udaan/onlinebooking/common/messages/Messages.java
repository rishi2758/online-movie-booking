package com.udaan.onlinebooking.common.messages;

public enum Messages {

	Success("0000","Success"),
	InvalidAisleValuesException("0001",""),
	InvalidBookingException("0002","");
	
	String code;
	String message; 
	
	private Messages(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
