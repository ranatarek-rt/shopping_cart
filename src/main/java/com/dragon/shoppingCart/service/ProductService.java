package com.dragon.shoppingCart.service;

import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.ProductDto;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    List<ProductDto> findAll();
    List<Product> findAllProductsByCategory(String category);
    List<Product> findAllProductsByBrand(String brand);
    List<Product> findProductsByCategoryAndBrand(String category,String brand);
    List<Product> findProductsByName(String productName);
    List<Product> findProductsByBrandAndName(String brand,String productName);
    Long countProductsByBrandAndName(String brand,String productName);
    ProductDto findById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductDto productDto,Long id);

}
