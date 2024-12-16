package com.dragon.shoppingCart.config;


import com.dragon.shoppingCart.entity.CartItem;
import com.dragon.shoppingCart.entity.Product;

import com.dragon.shoppingCart.model.CartItemDto;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.ImageRepo;
import com.dragon.shoppingCart.security.ShoppingUserDetailsService;
import com.dragon.shoppingCart.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ModelMapperFactory {

    ImageRepo imageRepo;
    ShoppingUserDetailsService userDetails;
    AuthenticationEntryPoint authPoint;
    private static final List<String> SECURED_URLS =
            List.of("/api/cart/**", "/api/cartItem/**");



    @Autowired
    public ModelMapperFactory(ImageRepo imageRepo, ShoppingUserDetailsService userDetails , AuthenticationEntryPoint authPoint){
        this.imageRepo = imageRepo;
        this.userDetails = userDetails;
        this.authPoint = authPoint;
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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetails);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }




}
