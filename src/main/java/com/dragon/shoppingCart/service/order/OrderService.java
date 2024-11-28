package com.dragon.shoppingCart.service.order;

import com.dragon.shoppingCart.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getAllOrdersByUserId(Long userId);


}
