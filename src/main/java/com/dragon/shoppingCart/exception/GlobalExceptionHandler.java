package com.dragon.shoppingCart.exception;


import com.dragon.shoppingCart.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCategoryNotFoundException (CategoryNotFoundException ex) {
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ApiResponse> handleDuplicatedCategoryException (DuplicateCategoryException ex) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ApiResponse> handleImageNotFoundException (ImageNotFoundException ex) {
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),null),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(new ApiResponse("Access Denied: You do not have permission to perform this action.",null), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleJwtException(JwtException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),null),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCartNotFoundException(CartNotFoundException ex){
        return new ResponseEntity<>(new ApiResponse(ex.getMessage(),null),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse> handleOrderNotFoundException(OrderNotFoundException ex){
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex){
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiResponse> handleDuplicateUserException(DuplicateUserException ex){
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicatedProductException.class)
    public ResponseEntity<ApiResponse> handleDuplicateProductException(DuplicatedProductException ex){
        return  new ResponseEntity<>(new ApiResponse(ex.getMessage(),null), HttpStatus.CONFLICT);
    }

}
