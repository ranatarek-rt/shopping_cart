package com.dragon.shoppingCart.controller;

import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto){
        Product product = productService.addProduct(productDto);
        return ResponseEntity.ok(new ApiResponse("successfully added a new product",product));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        List<ProductDto> productList = productService.findAll();
        return ResponseEntity.ok(new ApiResponse("successfully fetched",productList));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse>  findById(@PathVariable Long productId){
        ProductDto product = productService.findById(productId);
        return ResponseEntity.ok(new ApiResponse("successfully fetched",product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long productId){
         ProductDto product = productService.updateProduct(productDto,productId);
        return ResponseEntity.ok(new ApiResponse("updated successfully",product));
    }

    @GetMapping("/AllByCategory/{category}")
    public ResponseEntity<ApiResponse> findAllByCat(@PathVariable String category){
        List<ProductDto> productList = productService.findAllProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse("successfully fetched",productList));
    }

    @GetMapping("/AllByBrand/{brand}")
    public ResponseEntity<ApiResponse> findAllByBrand(@PathVariable String brand){
        List<ProductDto> productList = productService.findAllProductsByBrand(brand);
        return ResponseEntity.
                ok(new ApiResponse("all products are fetched successfully by brand name",productList));
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId){
        productService.deleteProductById(productId);
    }
}
