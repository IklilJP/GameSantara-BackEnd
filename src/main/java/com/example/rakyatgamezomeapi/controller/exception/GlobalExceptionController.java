package com.example.rakyatgamezomeapi.controller.exception;

import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.utils.exceptions.EmailAlreadyExistsException;
import com.example.rakyatgamezomeapi.utils.exceptions.FileCloudStorageException;
import com.example.rakyatgamezomeapi.utils.exceptions.ResourceNotFoundException;
import com.example.rakyatgamezomeapi.utils.exceptions.UsernameAlreadyExistException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        CommonResponse<Map<String, String>> commonResponse = CommonResponse.<Map<String, String>>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Form must be valid")
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.CONFLICT.value())
                .message("Terjadi masalah pada pengisian database")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CommonResponse<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<CommonResponse<String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(FileCloudStorageException.class)
    public ResponseEntity<CommonResponse<String>> handleFileCloudStorageException(FileCloudStorageException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

}
