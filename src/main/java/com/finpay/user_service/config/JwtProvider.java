package com.finpay.user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
                .signWith(getSigningKey(), Jwts.SIG.HS256) // Sử dụng chuẩn ký thuật toán mới của jjwt 0.12.x
                .compact();
    }


    // 1. Kiểm tra xác thực xem token có hợp lệ/hêt hạn không
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey()) // quét chữ ký bằng secret key của hệ thống
                    .build()
                    .parseSignedClaims(token);

            return true;
        }catch (Exception e){

            // Sẽ ném ra các lỗi nhưu ExpiredJwtException, MalformedJwtEception...
            System.out.println("Token khong hơợp lệ: "+e.getMessage());
        }

        return false;
    }

    // 2. Hàm lấy thông tin Username từ trong Payload của Token
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("username",String.class); // Trích xuất claim tùy biến ta đã gài vào lúc tạo
    }
}
