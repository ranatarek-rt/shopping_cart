package com.dragon.shoppingCart.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private List<CartItemDto> cartItems;
}
