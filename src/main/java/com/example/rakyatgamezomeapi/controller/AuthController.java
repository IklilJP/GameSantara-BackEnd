package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.model.dto.request.AuthRequest;
import com.example.rakyatgamezomeapi.model.dto.request.PasswordResetRequest;
import com.example.rakyatgamezomeapi.model.dto.request.RegisterUserRequest;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.LoginResponse;
import com.example.rakyatgamezomeapi.model.dto.response.RegisterResponse;
import com.example.rakyatgamezomeapi.service.AuthService;
import com.example.rakyatgamezomeapi.service.PasswordTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordTokenService passwordTokenService;

    @PostMapping("/register/user")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        RegisterResponse registerResponse = authService.registerUser(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Registered new user successfully")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/forgot-password/{email}")
    public ResponseEntity<CommonResponse<String>> forgotPassword(@PathVariable String email) {
        passwordTokenService.sendPasswordResetToken(email);
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Password reset email has been sent")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<CommonResponse<String>> resetPassword(@RequestParam String token, @RequestBody PasswordResetRequest passwordResetRequest) {
        passwordTokenService.updatePassword(token, passwordResetRequest.getPassword());
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Password has been reset successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerAdmin(@Valid @RequestBody RegisterUserRequest request) {
        RegisterResponse registerResponse = authService.registerAdmin(request);

        CommonResponse<RegisterResponse> commonResponse = CommonResponse.<RegisterResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Registered new admin successfully")
                .data(registerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@Valid @RequestBody AuthRequest request){
        LoginResponse loginResponse = authService.login(request);

        CommonResponse<LoginResponse> commonResponse = CommonResponse.<LoginResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Successfully login")
                .data(loginResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
