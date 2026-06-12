package com.finpay.user_service.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    //Hàm tạo chuỗi bí mật mã hóa dựa trên chuỗi cấu hình cấu hình yml
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Hàm chính: tạo sinh jwt token từ thông tin User
    public String generateToken(UUID userId, String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(userId.toString()) // Lưu trữ ID User vào phần sub
                .claim("username", username) // Lưu thêm thông tin tùy biến
                .claim("role", "ROLE_USER")  // Phân quyền mặc định
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sử dụng chuẩn ký thuật toán mới của jjwt 0.11.x
                .compact();
    }
}
