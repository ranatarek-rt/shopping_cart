package com.dragon.shoppingCart.service.order;

import com.dragon.shoppingCart.entity.Order;
import com.dragon.shoppingCart.model.OrderDto;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getAllOrdersByUserId(Long userId);


}
