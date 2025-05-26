package com.spring.exception;

public class StaffNotFoundException extends Exception {
	public StaffNotFoundException(String message) {
		super(message);
	}
}