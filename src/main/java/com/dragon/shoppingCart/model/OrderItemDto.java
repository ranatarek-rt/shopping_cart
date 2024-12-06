package com.dragon.shoppingCart.model;
import lombok.*;

import java.math.BigDecimal;



@Data
public class OrderItemDto {

    private Long productId;
    private int quantity;
    private BigDecimal price;

}
