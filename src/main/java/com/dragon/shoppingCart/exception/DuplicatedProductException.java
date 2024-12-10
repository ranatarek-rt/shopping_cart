package com.dragon.shoppingCart.exception;

public class DuplicatedProductException extends RuntimeException{

    public DuplicatedProductException(String message){
        super(message);
    }

}
