package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.model.AddItemToCartDto;
import com.dragon.shoppingCart.model.RemoveItemFromCartDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.cart.CartItemService;
import com.dragon.shoppingCart.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
    CartItemService cartItemService;
    CartService cartService;
    @Autowired
    CartItemController(CartItemService cartItemService,CartService cartService){
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId , @RequestBody AddItemToCartDto addItemToCartDto){
        if(cartId == null){
            cartId = cartService.initializeNewCart();

        }
        cartItemService.addItemToCart(cartId,addItemToCartDto);
        return ResponseEntity.ok(new ApiResponse("the item is added to cart successfully",null));

    }

    @DeleteMapping("/{cartId}/{productId}/removeAll")
    public ResponseEntity<ApiResponse> removeItemFromTheCart(@PathVariable Long cartId,@PathVariable Long productId){
        cartItemService.removeItemFromCart(cartId,productId);
        return ResponseEntity.ok(new ApiResponse("the item is removed from the cart successfully",null));
    }

    @PostMapping("/{cartId}/reduce-quantity")
    public ResponseEntity<ApiResponse> reduceItemQuantity(@PathVariable Long cartId,@RequestBody RemoveItemFromCartDto removeItemFromCartDto){
        cartItemService.reduceItemQuantity(cartId,removeItemFromCartDto);
        return ResponseEntity.ok(new ApiResponse("the item is quantity is reduced successfully",null));
    }

    @PutMapping("/{cartId}/update")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId , @RequestBody AddItemToCartDto addItemToCartDto){
        cartItemService.updateItemQuantity(cartId,addItemToCartDto);
        return ResponseEntity.ok(new ApiResponse("the item is quantity is updated successfully",null));
    }


}
