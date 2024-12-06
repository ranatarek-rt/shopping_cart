package com.dragon.shoppingCart.service.image;

import com.dragon.shoppingCart.entity.Image;
import com.dragon.shoppingCart.model.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> addImages(List<MultipartFile> files, Long productId);
    Image updateImage(MultipartFile file,Long imageId);
}
