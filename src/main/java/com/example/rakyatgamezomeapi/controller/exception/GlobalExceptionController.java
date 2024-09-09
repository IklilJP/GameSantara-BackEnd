package com.example.rakyatgamezomeapi.controller.exception;

import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.utils.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CommonResponse<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(TagAlreadyExistException.class)
    public ResponseEntity<CommonResponse<String>> handleTagAlreadyExistsException(TagAlreadyExistException ex) {
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<CommonResponse<String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(commonResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }

}
