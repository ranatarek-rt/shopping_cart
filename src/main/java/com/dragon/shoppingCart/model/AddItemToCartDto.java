package com.dragon.shoppingCart.model;

import lombok.*;
@Data
public class AddItemToCartDto {
    private Long productId;
    private int quantity;
}
