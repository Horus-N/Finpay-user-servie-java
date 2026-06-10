package com.finpay.user_service.servicce;
import com.finpay.user_service.dto.CreateUserRequest;
import com.finpay.user_service.dto.UserResponse;
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

    public UserResponse createUser(CreateUserRequest request){
        User user = new User(request.getName(),request.getEmail(),request.getPhone());
        User savedUser=userReposiory.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
    public List<User> getAll(){
        return userReposiory.findAll();
    }
}
