package com.spring.exception;

public class RoomTypeNotAvailable extends Exception {
    public RoomTypeNotAvailable(String message) {
        super(message);
    }
}