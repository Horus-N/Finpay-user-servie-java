package com.finpay.user_service.repository;

import com.finpay.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Tự động sinh câu lệnh kiểm tra tồn tại theo username
    boolean existsByUsername(String username);

    // Tự động sinh câu lệnh kiểm tra tồn tại theo email
    boolean existsByEmail(String email);
}