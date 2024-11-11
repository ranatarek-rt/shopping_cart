package com.dragon.shoppingCart.service;

import com.dragon.shoppingCart.repository.CategoryRepo;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    //inject category repo
    CategoryRepo categoryRepo;
    CategoryServiceImpl(CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }



}
