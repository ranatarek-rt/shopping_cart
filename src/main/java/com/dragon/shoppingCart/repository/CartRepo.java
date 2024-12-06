package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
     Cart findCartByUser(User user);
     Optional<Cart> findCartByUser_UserId(Long id);
     @Query("SELECT c FROM Cart c " +
             "JOIN FETCH c.cartItems ci " +
             "JOIN FETCH ci.product p " +
             "LEFT JOIN FETCH p.image " +
             "WHERE c.id = :id")
     Optional<Cart> findCartWithDetailsById(@Param("id") Long id);


}
