package com.finpay.user_service.controller;

import com.finpay.user_service.model.ApiResponse;
import com.finpay.user_service.model.dto.RegisterRequest;
import com.finpay.user_service.model.dto.UserResponse;
import com.finpay.user_service.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class Usercontroller {
    private final UserService userService;

    public Usercontroller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request){
        UserResponse response = userService.registerUser(request);
        return new ApiResponse<>("SUCCESS","Account register is success",response);
    }
}
