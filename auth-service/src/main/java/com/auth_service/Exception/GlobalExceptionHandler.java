package com.auth_service.Exception;

import com.auth_service.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;


@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exceptionHandler(Exception e, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getMessage());
        apiError.setMessage("Internal server error, please try again");
        apiError.setPath(request.getRequestURI());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(500).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getMessage());
        apiError.setMessage("Validation failed");
        apiError.setPath(request.getRequestURI());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setBackendMessage(e.getMessage());
        apiError.setMessage("Access Denied. You don't have permission to access this resource");
        apiError.setPath(request.getRequestURI());
        apiError.setMethod(request.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
