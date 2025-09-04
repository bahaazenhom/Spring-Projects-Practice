package com.bahaa.todo.exception;

import com.bahaa.todo.model.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentTypeMismatchException ex) {
        logger.severe("From Controller Advice - Validation error: " + ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        errorResponseDto.setMessage("Invalid argument type: " + ex.getName());
        errorResponseDto.setError("Bad Request");
        errorResponseDto.setPath(ex.getParameter().getParameterName());
        return ResponseEntity.status(errorResponseDto.getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessLogicException(BusinessLogicException ex, HttpServletRequest request) {
        logger.severe("From Controller Advice - Business logic error: " + ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        errorResponseDto.setMessage(ex.getMessage());
        errorResponseDto.setError("Bad Request");
        errorResponseDto.setPath(request.getRequestURI());
        return ResponseEntity.status(errorResponseDto.getStatus()).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, HttpServletRequest request) {
        logger.severe("From Controller Advice - An unexpected error occurred: " + ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        errorResponseDto.setMessage("An unexpected error occurred. Please try again later.");
        errorResponseDto.setError("Internal Server Error");
        errorResponseDto.setPath(request.getRequestURI());
        return ResponseEntity.status(errorResponseDto.getStatus()).body(errorResponseDto);
    }

}
