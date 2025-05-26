package com.spring.exception;

public class RoomAlreadyExists extends Exception {
	public RoomAlreadyExists(String message) {
		super(message);
	}
}