package com.example.backend.exception;

public class WeakPasswordException extends RuntimeException {

    public WeakPasswordException(String password) {
        super("Password is not strong: "+password);
    }

}