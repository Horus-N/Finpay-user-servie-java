package com.finpay.user_service.controller;

import com.finpay.user_service.dto.CreateUserRequest;
import com.finpay.user_service.dto.UserResponse;
import com.finpay.user_service.model.User;
import com.finpay.user_service.servicce.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UesrController {
    private final UserService userService;

    public UesrController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public UserResponse createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/api/users")
    public List<User> findAll(){
        return userService.getAll();
    }
}
