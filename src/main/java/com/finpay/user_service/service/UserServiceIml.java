package com.finpay.user_service.service;

import com.finpay.user_service.entity.User;
import com.finpay.user_service.exception.BadRequestException;
import com.finpay.user_service.exception.ResourceNotFoundException;
import com.finpay.user_service.model.dto.RegisterRequest;
import com.finpay.user_service.model.dto.UserResponse;
import com.finpay.user_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceIml(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username existed!");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email existed!");
        }
        // used passwordEncoder.encode() to hash password
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashedPassword)
                .status("ACTIVE")
                .createAt(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(user);
        return wrap(savedUser);
    }

    @Override
    public UserResponse getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found!"));
        return wrap(user);
    }

    public  UserResponse wrap(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

}
