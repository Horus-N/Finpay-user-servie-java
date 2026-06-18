package com.finpay.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

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
                        .anyRequest().permitAll()
                ));
        // Kỹ thuật vàng: đặt máy quét thẻ JWT Filter chạy trước máy lọc UsernamePasswordAuthenticationFilter mặc định
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
