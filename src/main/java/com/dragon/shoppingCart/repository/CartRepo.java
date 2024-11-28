package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
     Cart findCartByUser(User user);
     Optional<Cart> findCartByUser_UserId(Long id);
}
