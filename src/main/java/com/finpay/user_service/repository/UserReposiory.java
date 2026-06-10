package com.finpay.user_service.repository;

import com.finpay.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposiory extends JpaRepository<User,Long> {
}
