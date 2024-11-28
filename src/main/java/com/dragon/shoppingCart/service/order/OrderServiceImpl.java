package com.dragon.shoppingCart.service.order;
import com.dragon.shoppingCart.entity.*;

import com.dragon.shoppingCart.entity.OrderItem;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.exception.OrderNotFoundException;
import com.dragon.shoppingCart.repository.CartRepo;
import com.dragon.shoppingCart.repository.OrderRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import com.dragon.shoppingCart.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    CartRepo cartRepo;
    OrderRepo orderRepo;
    ProductRepo productRepo;
    CartService cartService;

    @Autowired
    OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, CartRepo cartRepo, CartService cartService){

        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.cartService = cartService;
    }

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartRepo
                .findCartByUser_UserId(userId)
                .orElseThrow(()-> new CartNotFoundException("there is no cart found for that user id "+ userId));
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order,cart);
        order.setOrderItemList(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmountOfOrder(orderItemList));
        Order savedOrder = orderRepo.save(order);
        cartService.emptyCart(cart.getId());
        return savedOrder;
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
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("there is no order found with id "+ orderId));
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
       return orderRepo.findByUser_UserId(userId);
    }

}
