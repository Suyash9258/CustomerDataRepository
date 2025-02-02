package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception{
	
	private HttpStatus errorMsg;
	private String errorCode;
	public ValidationException(HttpStatus errorMsg, String errorCode) {
		super();
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}

}
