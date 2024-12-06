package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUser_UserId(Long userId);
}
