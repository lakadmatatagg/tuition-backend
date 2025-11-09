package com.tigasatutiga.exception;

import com.tigasatutiga.models.ApiResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseModel<String>> handleBusinessException(BusinessException ex) {
        // Log the exception
        log.error("BusinessException occurred: ", ex);

        ApiResponseModel<String> response = new ApiResponseModel<>(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseModel<String>> handleGenericException(Exception ex) {
        // Log the exception
        log.error("Unexpected exception occurred: ", ex);

        ApiResponseModel<String> response = new ApiResponseModel<>(false, "An unexpected error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
