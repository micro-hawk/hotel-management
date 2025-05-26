package com.spring.exception;

public class RoomServiceAlreadyExists extends Exception {
	public RoomServiceAlreadyExists(String message) {
		super(message);
	}
}
