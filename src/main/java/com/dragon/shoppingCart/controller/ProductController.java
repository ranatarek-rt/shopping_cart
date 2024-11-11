package com.dragon.shoppingCart.controller;

import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    ProductService productService;
    @Autowired
    ProductController (ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }
    @GetMapping
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public Product findById(@PathVariable Long productId){
        return productService.findById(productId);
    }
    @PutMapping("/{productId}")
    public Product updateProduct(@RequestBody ProductDto productDto,@PathVariable Long productId){
         return productService.updateProduct(productDto,productId);
    }

    @GetMapping("/AllByCategory/{category}")
    public List<Product> findAllByCat(@PathVariable String category){
        return productService.findAllProductsByCategory(category);
    }


}
