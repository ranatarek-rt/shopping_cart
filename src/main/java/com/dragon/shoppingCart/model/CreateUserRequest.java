package com.dragon.shoppingCart.model;


import lombok.*;


@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
