package com.dragon.shoppingCart.service.cart;
import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.exception.UserNotFoundException;
import com.dragon.shoppingCart.model.CartDto;
import com.dragon.shoppingCart.repository.CartItemRepo;
import com.dragon.shoppingCart.repository.CartRepo;
import com.dragon.shoppingCart.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService{
    CartRepo cartRepo;
    CartItemRepo cartItemRepo;
    ModelMapper modelMapper;
    UserRepo userRepo;


    //inject cart repo
    @Autowired
    CartServiceImpl(CartRepo cartRepo,CartItemRepo cartItemRepo,ModelMapper modelMapper,UserRepo userRepo){
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;

    }


    @Override
    public CartDto getCartById(Long id) {
        Cart cart = cartRepo.findCartWithDetailsById(id)
                .orElseThrow(() -> new CartNotFoundException("There is no cart found with that ID " + id));


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

    @Transactional
    @Override
    public void deleteCart(Long id){
        cartRepo.findById(id).orElseThrow(()->new CartNotFoundException("there is no cart exists with id "+ id));
        cartRepo.deleteById(id);
    }
    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User sentUser){
        User user = userRepo.findById(sentUser.getUserId())
                .orElseThrow(()->new UserNotFoundException("there is no user found with that id "+ sentUser.getUserId()));
        return cartRepo.findCartByUser_UserId(sentUser.getUserId()).orElseGet(()->{
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepo.save(newCart);
                }
        );

    }
}
