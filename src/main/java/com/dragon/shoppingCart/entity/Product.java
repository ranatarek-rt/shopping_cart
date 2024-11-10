package com.dragon.shoppingCart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="brand")
    private String brand;
    @Column(name="description")
    private String description;
    @Column(name="price")
    private BigDecimal price;
    @Column(name="inventory_quantity")
    private int inventoryQuantity;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    // when you delete a product all the related images will be deleted
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> image;

}
