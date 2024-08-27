package com.example.rakyatgamezomeapi.controller.exception;

import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }
}
