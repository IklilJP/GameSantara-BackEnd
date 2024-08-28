package com.example.rakyatgamezomeapi.controller;

import com.example.rakyatgamezomeapi.constant.APIUrl;
import com.example.rakyatgamezomeapi.constant.ResponseMessage;
import com.example.rakyatgamezomeapi.model.dto.response.CommonResponse;
import com.example.rakyatgamezomeapi.model.dto.response.UserResponse;
import com.example.rakyatgamezomeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.USER_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CommonResponse<UserResponse>> getUserProfile() {
        UserResponse userResponse = userService.getUserByToken();
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User profile retrieved successfully")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
