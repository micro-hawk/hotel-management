package com.spring.exception;

public class RoomNotFoundException extends Exception {
	public RoomNotFoundException(String message) {
		super(message);
	}
}