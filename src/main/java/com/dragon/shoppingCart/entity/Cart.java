package com.dragon.shoppingCart.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name="total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    //to delete all cart items when the cart is deleted
    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();


    public void removeItem(CartItem item){
        cartItems.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }
    public void clearItem(){
        cartItems.clear();
        updateTotalAmount();
    }
    public void addItem(CartItem item){
        cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }


    public  void updateTotalAmount(){
        this.totalAmount = cartItems.stream().map(item->{
           BigDecimal itemPrice = item.getUnitPrice();
           if(itemPrice == null){
               return BigDecimal.ZERO;
           }
           return itemPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

        }).reduce(BigDecimal.ZERO , BigDecimal::add);
    }
}
