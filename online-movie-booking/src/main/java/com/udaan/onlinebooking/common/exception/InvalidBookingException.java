package com.udaan.onlinebooking.common.exception;

public class InvalidBookingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	
	public InvalidBookingException(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
