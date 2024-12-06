package com.dragon.shoppingCart.service.user;
import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.exception.DuplicateCategoryException;
import com.dragon.shoppingCart.exception.UserNotFoundException;
import com.dragon.shoppingCart.model.UpdateUserRequest;
import com.dragon.shoppingCart.model.CreateUserRequest;
import com.dragon.shoppingCart.model.UserDto;
import com.dragon.shoppingCart.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    UserRepo userRepo ;
    ModelMapper modelMapper;
    @Autowired
    UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper){
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto findUserById(Long userId) {
        return userRepo
                .findById(userId).map(user->modelMapper.map(user,UserDto.class))
                .orElseThrow(()-> new UserNotFoundException("there is no user found with id "+ userId));
    }

    @Override
    public UserDto createNewUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepo.findByEmail(createUserRequest.getEmail());
        if(user.isPresent()){
            throw new DuplicateCategoryException("there is a user already existing with that email "+ createUserRequest.getEmail());
        }

        User newUser = new User();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPassword(createUserRequest.getPassword());

        User toConvertUser =  userRepo.save(newUser);
        return modelMapper.map(toConvertUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest,Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID " + userId));

        //to avoid adding the fields as null in db if the user sending empty fields
        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
        User toConvertUser =  userRepo.save(user);
        return modelMapper.map(toConvertUser,UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID " + userId));
        userRepo.delete(user);
    }

}
