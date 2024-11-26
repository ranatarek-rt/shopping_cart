package com.dragon.shoppingCart.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddItemToCartDto {
    private Long productId;
    private int quantity;
}
