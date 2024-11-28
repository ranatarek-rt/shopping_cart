package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.model.CartDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    CartService cartService;

    @Autowired
    CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long cartId){
       CartDto cart =  cartService.getCartById(cartId);
       return ResponseEntity.ok(new ApiResponse("the cart is fetched successfully",cart));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> emptyCart(@PathVariable Long cartId){
        cartService.emptyCart(cartId);
        return ResponseEntity.ok(new ApiResponse("the cart is emptied successfully",null));
    }

    @GetMapping("/price/{cartId}")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId){
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("the total price is fetched successfully", totalPrice));
    }

}
