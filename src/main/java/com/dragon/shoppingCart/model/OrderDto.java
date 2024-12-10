package com.dragon.shoppingCart.model;
import com.dragon.shoppingCart.entity.OrderItem;
import com.dragon.shoppingCart.entity.OrderStatus;
import com.dragon.shoppingCart.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class OrderDto {

    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItemList;


}
