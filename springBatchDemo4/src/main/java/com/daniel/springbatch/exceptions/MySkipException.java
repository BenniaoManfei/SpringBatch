package com.daniel.springbatch.exceptions;

public class MySkipException extends RuntimeException {
	
	public MySkipException(String message) {
		super(message);
	}
}
