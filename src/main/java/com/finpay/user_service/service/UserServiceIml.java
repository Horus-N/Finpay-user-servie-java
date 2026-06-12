package com.finpay.user_service.service;

import com.finpay.user_service.client.WalletClient;
import com.finpay.user_service.config.JwtProvider;
import com.finpay.user_service.entity.User;
import com.finpay.user_service.exception.BadRequestException;
import com.finpay.user_service.exception.ResourceNotFoundException;
import com.finpay.user_service.model.dto.AuthResponse;
import com.finpay.user_service.model.dto.LoginRequest;
import com.finpay.user_service.model.dto.RegisterRequest;
import com.finpay.user_service.model.dto.UserResponse;
import com.finpay.user_service.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletClient walletClient;
    private final JwtProvider jwtProvider;

    public UserServiceIml(UserRepository userRepository, PasswordEncoder passwordEncoder, WalletClient walletClient, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletClient = walletClient;
        this.jwtProvider = jwtProvider;
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
        //GỌI SANG WALLET SERVICE QUA FEIGN CLIENT
        try{
            Map<String, Object> walletRequest = Map.of("userId", savedUser.getId());
            walletClient.triggerCreateWallet(walletRequest);
        } catch (Exception e) {
            throw new RuntimeException("Cannot init wallet!");
        }
        return wrap(savedUser);
    }

    @Override
    public UserResponse getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found!"));
        return wrap(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        //1. Tìm user theo username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new BadRequestException("Username or password is not incorrect!")) ;
        //2. Kiểm tra và so khớp mật khẩu bằng paswordEncoder.matches()
        // Tuyệt đối không dùng dấu == hay hàm.equals() vì pass trong DB đã bị băm nát bằng BCrypt
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new BadRequestException("Username or password is not incorrect!") ;
        }
        // 3. Nếu đúng mật khẩu, tiến hành tạo chuỗi token
        String token = jwtProvider.generateToken(user.getId(),user.getUsername());
        return new AuthResponse(token,"Bearer");
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
