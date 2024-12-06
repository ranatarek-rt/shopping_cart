package com.dragon.shoppingCart.service.cart;
import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.model.CartDto;

import java.math.BigDecimal;

public interface CartService {
    CartDto getCartById(Long id);
    void emptyCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(Long userId);
}
