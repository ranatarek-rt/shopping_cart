package com.dragon.shoppingCart.service;


import com.dragon.shoppingCart.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    //inject image repo
    ImageRepo imageRepo;
    @Autowired
    ImageServiceImpl(ImageRepo imageRepo){
        this.imageRepo = imageRepo;
    }
}
