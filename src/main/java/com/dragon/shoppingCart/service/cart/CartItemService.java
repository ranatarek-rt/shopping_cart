package com.dragon.shoppingCart.service.cart;

import com.dragon.shoppingCart.entity.CartItem;
import com.dragon.shoppingCart.model.AddItemToCartDto;
import com.dragon.shoppingCart.model.CartItemDto;
import com.dragon.shoppingCart.model.RemoveItemFromCartDto;

public interface CartItemService {
    CartItemDto findCartItemById(Long cartItemId);
    void addItemToCart(Long cartId, AddItemToCartDto itemToCartDto);
    void removeItemFromCart(Long CartId,Long productId);
    void updateItemQuantity(Long cartId, AddItemToCartDto itemToCartDto);
    void reduceItemQuantity(Long cartId, RemoveItemFromCartDto removeItemFromCartDto);
}
