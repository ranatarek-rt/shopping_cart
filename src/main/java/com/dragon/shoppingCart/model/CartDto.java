package com.dragon.shoppingCart.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private BigDecimal totalAmount;
    private Set<CartItemDto> cartItems;
}
