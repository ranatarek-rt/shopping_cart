package com.dragon.shoppingCart.model;


import lombok.*;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDto product;
}
