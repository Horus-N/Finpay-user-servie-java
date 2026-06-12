package com.finpay.user_service.controller;

import com.finpay.user_service.model.ApiResponse;
import com.finpay.user_service.model.dto.AuthResponse;
import com.finpay.user_service.model.dto.LoginRequest;
import com.finpay.user_service.service.UserService;
import com.finpay.user_service.service.UserServiceIml;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserServiceIml userServiceIml;

    public AuthController(UserServiceIml userServiceIml) {
        this.userServiceIml = userServiceIml;
    }
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userServiceIml.login(request);
        return new ApiResponse<>("SUCCESS", "Đăng nhập FinPay thành công!", response);
    }

}
