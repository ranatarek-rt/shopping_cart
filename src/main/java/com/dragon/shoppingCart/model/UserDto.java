package com.dragon.shoppingCart.model;
import lombok.*;

import java.util.List;


@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> order;
    private CartDto cart;

}
