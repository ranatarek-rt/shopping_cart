package com.dragon.shoppingCart.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="file_name")
    private String fileName;
    @Column(name="file_path")
    private String filePath;

    @Column(name="product_image")
    @Lob
    private Blob productImage;

    @Column(name="download_url")
    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
