package com.bahaa.todo.exception.security;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthenticationFailedException(Throwable cause) {
        super(cause);
    }
}
