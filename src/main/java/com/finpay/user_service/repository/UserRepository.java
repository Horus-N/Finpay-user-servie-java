package com.finpay.user_service.repository;

import com.finpay.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
    Magic là khi gọi UserRepository.existsByEmail là như
    SELECT *
FROM users
WHERE email = ?
     */
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<User> findByEmail(String email);

    User getById(String id);
}
