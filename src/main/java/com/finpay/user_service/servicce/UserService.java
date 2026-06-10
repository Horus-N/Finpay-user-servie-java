package com.finpay.user_service.servicce;
import com.finpay.user_service.model.User;
import com.finpay.user_service.repository.UserReposiory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserReposiory userReposiory;

    public UserService(UserReposiory userReposiory) {
        this.userReposiory = userReposiory;
    }

    public User createUser(){
        User user = new User("Tung","tung@finpay.com","0366825614");
        return userReposiory.save(user);
    }
    public List<User> getAll(){
        return userReposiory.findAll();
    }
}
