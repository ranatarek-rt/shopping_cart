package com.dragon.shoppingCart.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="order")
public class Order {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name="order_date")
    private LocalDate orderDate;

    @Column(name="total_amount")
    private BigDecimal totalAmount;

    @Column(name="order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",cascade =CascadeType.ALL,orphanRemoval = true)
    private Set<OrderItem> orderItemList = new HashSet<>();

}
