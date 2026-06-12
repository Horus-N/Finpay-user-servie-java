package com.finpay.user_service.service;

import com.finpay.user_service.model.dto.AuthResponse;
import com.finpay.user_service.model.dto.LoginRequest;
import com.finpay.user_service.model.dto.RegisterRequest;
import com.finpay.user_service.model.dto.UserResponse;

import java.util.UUID;

public interface UserService {
    UserResponse registerUser (RegisterRequest request);
    UserResponse getUser(UUID id);
    AuthResponse login(LoginRequest request);
}