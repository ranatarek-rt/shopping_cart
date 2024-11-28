package com.dragon.shoppingCart.service.cart;
import com.dragon.shoppingCart.model.CartDto;

import java.math.BigDecimal;

public interface CartService {
    CartDto getCartById(Long id);
    void emptyCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
