package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Image;
import com.dragon.shoppingCart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
    List<Image> findAllByProduct(Product product);
}
