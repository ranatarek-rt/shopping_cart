package com.dragon.shoppingCart.exception;

public class DuplicateCategoryException extends RuntimeException{
    public DuplicateCategoryException(String message){
        super(message);
    }
}
