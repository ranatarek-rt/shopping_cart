package com.dragon.shoppingCart.service.product;

import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.ProductDto;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    List<ProductDto> findAll();
    List<ProductDto> findAllProductsByCategory(String category);
    List<ProductDto> findAllProductsByBrand(String brand);
    List<ProductDto> findProductsByCategoryAndBrand(String category,String brand);
    List<ProductDto> findProductsByName(String productName);
    List<ProductDto> findProductsByBrandAndName(String brand,String productName);
    Long countProductsByBrandAndName(String brand,String productName);
    ProductDto findById(Long id);
    void deleteProductById(Long id);
    ProductDto updateProduct(ProductDto productDto,Long id);

}
