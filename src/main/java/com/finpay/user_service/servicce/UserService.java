package com.finpay.user_service.servicce;
import com.finpay.user_service.dto.CreateUserRequest;
import com.finpay.user_service.dto.LoginRequest;
import com.finpay.user_service.dto.LoginResponse;
import com.finpay.user_service.dto.UserResponse;
import com.finpay.user_service.exception.BusinessException;
import com.finpay.user_service.model.User;
import com.finpay.user_service.repository.UserRepository;
import com.finpay.user_service.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse createUser(CreateUserRequest request){
        if(userRepository.existsByEmail(request.getEmail())) throw new BusinessException("User email already registered!");
        if(userRepository.existsByPhone(request.getPhone())) throw new BusinessException("Phone already exists!");

        String hashedPassword = passwordEncoder.encode("123456");
        User user = new User(request.getName(),request.getEmail(),request.getPhone(),hashedPassword,"USER");
        User savedUser= userRepository.save(user);
        return map(savedUser);
    }

    public UserResponse getOne(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new BusinessException("User not found!"));
        return map(user);
    }

    public UserResponse updateOne(Long id, CreateUserRequest request){
        User user = userRepository.findById(id).orElseThrow(()->new BusinessException("User not found!"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        User savedUser= userRepository.save(user);
        return map(savedUser);
    }

    public void deleteOne(Long id){
        userRepository.deleteById(id);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new BusinessException("User not found!"));
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new BusinessException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());
        LoginResponse admin = new LoginResponse(token);
        return admin;
    }

    private UserResponse map(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
