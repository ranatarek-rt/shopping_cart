package com.dragon.shoppingCart.model;


import lombok.*;

@Data
public class RemoveItemFromCartDto {
    private Long productId;
    private int quantityToReduce;
}
