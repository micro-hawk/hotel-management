package com.spring.exception;



public class UserErrorException extends Exception {
    public UserErrorException(String msg) {
        super(msg);
    }
}