package com.finpay.user_service.controller;

import com.finpay.user_service.model.ApiResponse;
import com.finpay.user_service.model.dto.RegisterRequest;
import com.finpay.user_service.model.dto.UserResponse;
import com.finpay.user_service.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable UUID id){
        UserResponse response = userService.getUser(id);
        return new ApiResponse<>("SUCCess","Get user is success",response );
    }

    @GetMapping("/me")
    public ApiResponse<?> getMe(){
        String currentUsername = (String)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();


        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        List<String> roles =
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
        Object currentUser = Map.of(
                "username", currentUsername,
                "roles", roles
        );
        return new ApiResponse<>("SUCCess","Get user is success",currentUser);
    }

}
