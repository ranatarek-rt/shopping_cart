package com.dragon.shoppingCart.service;
import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.model.CartDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.CartItemRepo;
import com.dragon.shoppingCart.repository.CartRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService{
    CartRepo cartRepo;
    CartItemRepo cartItemRepo;
    ModelMapper modelMapper;
    AtomicLong cartIdGenerator = new AtomicLong(0);


    //inject cart repo
    @Autowired
    CartServiceImpl(CartRepo cartRepo,CartItemRepo cartItemRepo,ModelMapper modelMapper){
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public CartDto getCartById(Long id) {
        Cart cart =  cartRepo.findById(id).
                orElseThrow(()->new CartNotFoundException("there is no cart found with that id "+ id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        Cart savedCart = cartRepo.save(cart);
       return modelMapper.map(savedCart,CartDto.class);
    }

    @Transactional
    @Override
    public void emptyCart(Long id) {
        CartDto cart =  getCartById(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepo.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart(){
        Cart cart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        cart.setId(newCartId);
        return cartRepo.save(cart).getId();
    }
}
