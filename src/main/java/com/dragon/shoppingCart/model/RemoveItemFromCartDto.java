package com.dragon.shoppingCart.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemoveItemFromCartDto {
    private Long productId;
    private int quantityToReduce;
}
