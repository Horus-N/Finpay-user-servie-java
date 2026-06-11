package com.finpay.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 1. Khai báo Bean mã háo mật khẩu để sử dụng ở khắp mọi nơi trong dự án
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();// Sử dụng thuạt tóa BCrypt chuẩn hóa
    }
    // 2. Cấu hình phân quyền cho các API Endpoints
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf-> csrf.disable()) //Vô hiệu hóa CSRF bảo vệ
                .authorizeHttpRequests((auth->auth
                        // Cho phép TẤT CẢ các request đến đường dẫn register và health check mà không cần đăng nhập
                        .requestMatchers("/api/v1/users/register", "/api/v1/health", "/api/v1/hello").permitAll()
                        // Tất cả các request khác (ví dụ lấy chi tiết user) tạm thời bắt buộc phải đăng nhập
                        .anyRequest().authenticated()
                ));
        return http.build();
    }
}
