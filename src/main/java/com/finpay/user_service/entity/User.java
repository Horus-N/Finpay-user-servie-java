package com.finpay.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity // Đánh dấu đây là 1 bảng trong DB
@Table(name = "users") // Tên bảng sẽ là "users"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Các annotation của Lombok giúp tự tạo getter/setter mà không cần gõ code
public class User {

    @Id // Đánh dấu đây là Khóa chính
    @GeneratedValue(strategy = GenerationType.UUID) // Tự động sinh ID theo chuẩn UUID
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 50)
    private String status;
    private LocalDateTime createAt;
}