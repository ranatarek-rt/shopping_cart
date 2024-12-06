package com.dragon.shoppingCart.service.user;

import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.model.UpdateUserRequest;
import com.dragon.shoppingCart.model.CreateUserRequest;
import com.dragon.shoppingCart.model.UserDto;

public interface UserService {

    UserDto findUserById(Long userId);
    UserDto createNewUser(CreateUserRequest createUserRequest);
    UserDto updateUser(UpdateUserRequest updateUserRequest,Long userId);
    void deleteUser(Long userId);
}
