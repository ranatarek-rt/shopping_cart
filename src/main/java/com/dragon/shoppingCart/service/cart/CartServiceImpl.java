package com.dragon.shoppingCart.service.cart;
import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.entity.User;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.exception.UserNotFoundException;
import com.dragon.shoppingCart.model.CartDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.CartItemRepo;
import com.dragon.shoppingCart.repository.CartRepo;
import com.dragon.shoppingCart.repository.UserRepo;
import com.dragon.shoppingCart.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
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

    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cart = getCartById(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotFoundException("there is no user found with that id "+ userId));
        return cartRepo.findCartByUser_UserId(userId).orElseGet(()->{
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepo.save(newCart);
                }
        );

    }
}
