package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.model.AddItemToCartDto;
import com.dragon.shoppingCart.model.CartItemDto;
import com.dragon.shoppingCart.model.RemoveItemFromCartDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.cart.CartItemService;
import com.dragon.shoppingCart.service.cart.CartService;
import com.dragon.shoppingCart.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
    CartItemService cartItemService;
    CartService cartService;
    UserService userService;
    @Autowired
    CartItemController(CartItemService cartItemService,CartService cartService){
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> getCartItemById(@PathVariable Long cartItemId){
        CartItemDto cartItem = cartItemService.findCartItemById(cartItemId);
        return ResponseEntity.ok(new ApiResponse("the cart items are fetched successfully",cartItem));
    }



    @PostMapping
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long userId, @RequestBody AddItemToCartDto addItemToCartDto){
        User user = userService.getAuthenticatedUser();
        Cart cart = cartService.initializeNewCart(user);
        cartItemService.addItemToCart(cart.getId(),addItemToCartDto);
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
