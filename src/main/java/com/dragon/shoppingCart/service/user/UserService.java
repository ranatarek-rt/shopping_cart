package com.dragon.shoppingCart.service.user;

import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.request.UpdateUserRequest;
import com.dragon.shoppingCart.request.CreateUserRequest;
import com.dragon.shoppingCart.model.UserDto;
import jakarta.transaction.Transactional;

public interface UserService {

    UserDto findUserById(Long userId);
    UserDto createNewUser(CreateUserRequest createUserRequest);
    UserDto updateUser(UpdateUserRequest updateUserRequest,Long userId);
    void deleteUser(Long userId);

    User getAuthenticatedUser();

    @Transactional
    void assignRoleToUser(Long userId, String roleName);
}
