package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
            List<Product> findByCategoryName(String category);
            List<Product> findAllByBrand(String brand);
            List<Product> findAllByCategoryNameAndBrand(String category,String brand);
            List<Product> findAllByName(String name);
            List<Product> findAllByBrandAndName(String brand,String name);
            Long countAllByBrandAndName(String brand,String name);
}
