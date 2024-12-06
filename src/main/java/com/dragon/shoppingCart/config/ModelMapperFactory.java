package com.dragon.shoppingCart.config;

import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.CartItem;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.CartDto;
import com.dragon.shoppingCart.model.CartItemDto;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.ImageRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperFactory {

    ImageRepo imageRepo;
    @Autowired
    public ModelMapperFactory(ImageRepo imageRepo){
        this.imageRepo = imageRepo;

    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        // if you do not need the product images to be displayed with the cart or cartItem you can remove this mapping
        modelMapper.typeMap(CartItem.class, CartItemDto.class)
                .setPostConverter(context -> {
                    CartItem cartItem = context.getSource();
                    CartItemDto cartItemDto = context.getDestination();

                    // Map Product to ProductDto and set it
                    ProductDto productDto = modelMapper.map(cartItem.getProduct(), ProductDto.class);
                    cartItemDto.setProduct(productDto);

                    return cartItemDto;
                });


        // Implement a custom model mapping for images
        modelMapper.typeMap(Product.class, ProductDto.class)
                .addMappings(mapper -> mapper.skip(ProductDto::setImages))
                .setPostConverter(context -> {
                    Product product = context.getSource();
                    ProductDto productDto = context.getDestination();

                    // Safely set images using Optional
                    productDto.setImages(
                            Optional.ofNullable(imageRepo.findAllByProduct(product))
                                    .orElse(Collections.emptyList()) // Use an empty list if null
                                    .stream()
                                    .map(image -> modelMapper.map(image, ImageDto.class)) // Map Image -> ImageDto
                                    .collect(Collectors.toList()) // Collect as a list
                    );

                    return productDto;
                });

        return modelMapper;
    }




}
