package com.dragon.shoppingCart.service.image;

import com.dragon.shoppingCart.entity.Image;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.exception.ImageNotFoundException;
import com.dragon.shoppingCart.exception.ProductNotFoundException;
import com.dragon.shoppingCart.model.ImageDto;
import com.dragon.shoppingCart.repository.ImageRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageServiceImpl implements ImageService {

    //inject image repo
    ImageRepo imageRepo;
    ProductRepo productRepo;
    ModelMapper modelMapper;
    @Autowired
    ImageServiceImpl(ImageRepo imageRepo, ProductRepo productRepo,
                      ModelMapper modelMapper){
        this.imageRepo = imageRepo;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Image getImageById(Long id) {

        return imageRepo.findById(id)
                        .orElseThrow(()->
                                new ImageNotFoundException("no image found with that id "+ id));
    }

    @Override
    public void deleteImage(Long id) {
        if(imageRepo.findById(id).isPresent()){
            imageRepo.deleteById(id);
            return;
        }
        throw new ImageNotFoundException("no image found with that id "+ id);

    }

    @Override
    public List<ImageDto> addImages(List<MultipartFile> files, Long productId){
        Product product = productRepo.findById(productId)
                .orElseThrow(()->new ProductNotFoundException("there is no product found with that id " + productId));
        List<ImageDto> imageDtoList= new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProductImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepo.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepo.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDtoList.add(imageDto);

            } catch (RuntimeException | SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageDtoList;

    }

    @Override
    public ImageDto updateImage(MultipartFile file, Long imageId) {
        //we can use the image service method that we created to fetch the image
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setProductImage(new SerialBlob(file.getBytes()));
            image.setFileType(file.getContentType());
            Image savedImage = imageRepo.save(image);
            return modelMapper.map(savedImage,ImageDto.class);
        }catch(RuntimeException | IOException | SQLException e){
            throw new RuntimeException("failed to process the image" + e.getMessage());
        }
    }

}
