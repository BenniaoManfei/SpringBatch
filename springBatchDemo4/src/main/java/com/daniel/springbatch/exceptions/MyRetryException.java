package com.daniel.springbatch.exceptions;

public class MyRetryException extends RuntimeException {
	public MyRetryException(String message) {
		super(message);
	}
}
