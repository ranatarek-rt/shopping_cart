package com.dragon.shoppingCart.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message){
        super(message);
    }

}
