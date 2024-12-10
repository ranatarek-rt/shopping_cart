package com.dragon.shoppingCart.service.order;
import com.dragon.shoppingCart.entity.*;

import com.dragon.shoppingCart.entity.OrderItem;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.exception.OrderNotFoundException;
import com.dragon.shoppingCart.model.OrderDto;
import com.dragon.shoppingCart.repository.CartRepo;
import com.dragon.shoppingCart.repository.OrderRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import com.dragon.shoppingCart.service.cart.CartService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    CartRepo cartRepo;
    OrderRepo orderRepo;
    ProductRepo productRepo;
    CartService cartService;
    EntityManager entityManager;

    @Autowired
    OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, CartRepo cartRepo, CartService cartService, ModelMapper modelMapper,EntityManager entityManager){

        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartRepo
                .findCartByUser_UserId(userId)
                .orElseThrow(() -> new CartNotFoundException("there is no cart found for that user id " + userId));

        // Create order and associated items
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItemList(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmountOfOrder(orderItemList));
        Order savedOrder = orderRepo.save(order);

        // Clear cart items
        cart.getCartItems().clear();

        // Remove cart from user
        User user = cart.getUser();
        if (user != null) {
            user.setCart(null); // Disassociate the cart from the user
        }

        // Delete the cart
        cartRepo.delete(cart);

        return modelMapper.map(savedOrder, OrderDto.class);
    }



    private Order createOrder(Cart cart){
        Order order = new Order();
        // set the user
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.pending);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem->{
            Product product = cartItem.getProduct();
            product.setInventoryQuantity(product.getInventoryQuantity() - cartItem.getQuantity());
            productRepo.save(product);
            return new OrderItem(cartItem.getUnitPrice(),cartItem.getQuantity(),order,product);
                }).toList();
    }


    private BigDecimal calculateTotalAmountOfOrder(List<OrderItem> orderItemList) {
        BigDecimal sumAmount = BigDecimal.ZERO;

        for (OrderItem orderItem : orderItemList) {
            BigDecimal itemTotal = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            sumAmount = sumAmount.add(itemTotal);
        }
        return sumAmount;
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepo.findById(orderId).map(order->modelMapper.map(order, OrderDto.class))
                .orElseThrow(()-> new OrderNotFoundException("there is no order found with id "+ orderId));
    }

    @Override
    public List<OrderDto> getAllOrdersByUserId(Long userId) {
       return orderRepo.findByUser_UserId(userId).stream().map(order->modelMapper.map(order,OrderDto.class)).toList();
    }

}
