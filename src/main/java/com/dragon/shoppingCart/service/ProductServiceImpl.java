package com.dragon.shoppingCart.service;
import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.entity.Image;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.exception.ProductNotFoundException;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.CategoryRepo;
import com.dragon.shoppingCart.repository.ImageRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//we can use this lombok annotation for constructor injection
// instead of doing it , and you should set the value to be injected as final
//@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    //inject the product repo
    ProductRepo productRepo;
    ModelMapper modelMapper;
    CategoryRepo categoryRepo;
    ImageRepo imageRepo;
    @Autowired
    ProductServiceImpl(ProductRepo productRepo,ModelMapper modelMapper,
                       CategoryRepo categoryRepo,ImageRepo imageRepo){
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
        this.imageRepo = imageRepo;


    }
    @Override
    public Product addProduct(ProductDto productDto) {
        // Find the category by name
        Optional<Category> optionalCat = categoryRepo.findCategoryByName(productDto.getCategory().getName());
        Product product = modelMapper.map(productDto, Product.class);
        // Check if the category exists
        if (optionalCat.isPresent()) {
            // If category exists set it on the product
            product.setCategory(optionalCat.get());
        } else {
            // If the category doesn't exist save the new category
            Category newCategory = new Category(productDto.getCategory().getName());
            categoryRepo.save(newCategory);
            product.setCategory(newCategory);
        }
        //after finishing save the product in the db and return
        productRepo.save(product);

        return product;
    }

    @Override
    public Product updateProduct(ProductDto productDto, Long id) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        // Update basic product fields
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setInventoryQuantity(productDto.getInventoryQuantity());

        // Handle category
        if (productDto.getCategory() != null) {
            String categoryName = productDto.getCategory().getName();
            Category category = categoryRepo.findCategoryByName(categoryName)
                    .orElseGet(() -> categoryRepo.save(new Category(categoryName)));  // Create if not exists
            existingProduct.setCategory(category); // Set the category to the product
        }

        // Save the updated product
        return productRepo.save(existingProduct);
    }


    //*********************** search, get all, getById,DeleteById ********************
    //find all products
    @Override
    public List<ProductDto> findAll() {
        List<Product> productList = productRepo.findAll();
        return productList.stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.
                            setImages(imageRepo
                                    .findAllByProduct(product)
                                    .stream()
                                    .map(image->modelMapper.map(image, ImageDto.class)).toList()); // Enrich DTO with images
                    return productDto;
                })
                .toList();
    }

    @Override
    public List<Product> findAllProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> findAllProductsByBrand(String brand) {
        return productRepo.findAllByBrand(brand);
    }

    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findAllByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> findProductsByName(String productName) {
        return productRepo.findAllByName(productName);
    }

    @Override
    public List<Product> findProductsByBrandAndName(String brand, String productName) {
        return productRepo.findAllByBrandAndName(brand,productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return  productRepo.countAllByBrandAndName(brand,productName);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return product.orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        product.ifPresentOrElse(
                value -> productRepo.delete(value),
                () -> { throw new ProductNotFoundException("Product not found"); }
        );
    }


}
