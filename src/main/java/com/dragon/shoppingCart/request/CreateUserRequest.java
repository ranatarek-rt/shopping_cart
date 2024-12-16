package com.dragon.shoppingCart.request;


import lombok.*;


@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
