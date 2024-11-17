package com.dragon.shoppingCart.config;

import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.ImageRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperFactory {

    ImageRepo imageRepo;
    @Autowired
    public ModelMapperFactory(ImageRepo imageRepo){
        this.imageRepo = imageRepo;

    }
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //implement a custom model mapping for images
        modelMapper.typeMap(Product.class, ProductDto.class)
                .addMappings(mapper -> mapper.skip(ProductDto::setImages))
                .setPostConverter(context -> {
                    Product product = context.getSource();
                    ProductDto productDto = context.getDestination();
                    productDto.setImages(imageRepo
                            .findAllByProduct(product)
                            .stream()
                            .map(image->modelMapper.map(image, ImageDto.class)).toList());
                    return productDto;
                });
        return modelMapper;
    }
}
