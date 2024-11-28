package com.dragon.shoppingCart.service.user;
import com.dragon.shoppingCart.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    UserRepo userRepo ;
    @Autowired
    UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }
}
