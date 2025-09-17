package com.bahaa.todo.exception;

public class NoAuthenticatedUser extends RuntimeException{
    public NoAuthenticatedUser(String message) {
        super(message);
    }
    public NoAuthenticatedUser(String message, Throwable cause) {
        super(message, cause);
    }
    public NoAuthenticatedUser(Throwable cause) {
        super(cause);
    }
}
