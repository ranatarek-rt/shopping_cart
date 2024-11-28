package com.dragon.shoppingCart.service.order;

import com.dragon.shoppingCart.repository.OrderItemRepo;
import org.springframework.stereotype.Service;

@Service
public class OrderItemImpl implements OrderItem{
    OrderItemRepo orderItemRepo;

    OrderItemImpl(OrderItemRepo orderItemRepo){
        this.orderItemRepo = orderItemRepo;
    }



}
