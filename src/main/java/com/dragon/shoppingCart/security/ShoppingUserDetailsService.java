package com.dragon.shoppingCart.security;
import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.exception.UserNotFoundException;
import com.dragon.shoppingCart.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ShoppingUserDetailsService implements UserDetailsService {

    UserRepo userRepo;
    @Autowired
    public ShoppingUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()->new UserNotFoundException("there is no user found with that email"));
        return ShoppingUserDetails.buildUserDetails(user);
    }
}
