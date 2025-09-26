package com.bahaa.todo.exception;

import com.bahaa.todo.exception.user.InvalidCredentialsException;
import com.bahaa.todo.exception.category.CategoryNotFoundException;
import com.bahaa.todo.exception.security.AuthenticationFailedException;
import com.bahaa.todo.exception.task.TaskNotFoundException;
import com.bahaa.todo.exception.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ================================
    // Category Exceptions
    // ================================

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(
            CategoryNotFoundException ex, WebRequest request) {

        logger.warn("Category not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Category Not Found")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ================================
    // Security Exceptions
    // ================================

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailed(
            AuthenticationFailedException ex, WebRequest request) {

        logger.warn("Authentication failed: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Authentication Failed")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // ================================
    // Task Exceptions
    // ================================

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(
            TaskNotFoundException ex, WebRequest request) {

        logger.warn("Task not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Task Not Found")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ================================
    // User Exceptions
    // ================================

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(

            InvalidCredentialsException ex, WebRequest request) {

        logger.warn("Invalid credentials: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Credentials")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccess(
            UnauthorizedAccessException ex, WebRequest request) {

        logger.warn("Unauthorized access attempt: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Unauthorized Access")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(
            UserAlreadyExistsException ex, WebRequest request) {

        logger.warn("User already exists: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("User Already Exists")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex, WebRequest request) {

        logger.warn("User not found: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("User Not Found")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ================================
    // Business Logic & General Exceptions
    // ================================

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogic(
            BusinessLogicException ex, WebRequest request) {

        logger.warn("Business logic error: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Business Logic Error")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAuthenticatedUser.class)
    public ResponseEntity<ErrorResponse> handleNoAuthenticatedUser(
            NoAuthenticatedUser ex, WebRequest request) {

        logger.warn("No authenticated user: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Authentication Required")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // ================================
    // Generic Exception Handler
    // ================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        logger.error("Unexpected error occurred: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ================================
    // Error Response DTO
    // ================================

    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        // Constructors
        public ErrorResponse() {}

        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

        // Builder pattern
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private ErrorResponse errorResponse = new ErrorResponse();

            public Builder timestamp(LocalDateTime timestamp) {
                errorResponse.timestamp = timestamp;
                return this;
            }

            public Builder status(int status) {
                errorResponse.status = status;
                return this;
            }

            public Builder error(String error) {
                errorResponse.error = error;
                return this;
            }

            public Builder message(String message) {
                errorResponse.message = message;
                return this;
            }

            public Builder path(String path) {
                errorResponse.path = path;
                return this;
            }

            public ErrorResponse build() {
                return errorResponse;
            }
        }

        // Getters and Setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getPath() { return path; }
        public void setPath(String path) { this.path = path; }
    }
}