package com.bahaa.todo.exception.user;

public class InvalidCredentialsException extends  Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }
}
