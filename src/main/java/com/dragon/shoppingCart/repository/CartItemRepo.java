package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByCartId(Long id);
    void deleteAllByCartId(Long id);
}
