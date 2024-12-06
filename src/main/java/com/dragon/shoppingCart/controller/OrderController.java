package com.dragon.shoppingCart.controller;


import com.dragon.shoppingCart.entity.Order;
import com.dragon.shoppingCart.model.OrderDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;

    @Autowired
    OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUserOrder(@RequestParam Long userId){
        Order order = orderService.placeOrder(userId);
        return ResponseEntity.ok(new ApiResponse("the ordered is created successfully",order));
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("the ordered is fetched successfully",order));
    }

    @GetMapping("/AllOrders/{userId}")
    public ResponseEntity<ApiResponse> getAllUserOrders(@PathVariable Long userId){
        List<OrderDto> orderList = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("All user orders are fetched successfully",orderList));
    }

}
