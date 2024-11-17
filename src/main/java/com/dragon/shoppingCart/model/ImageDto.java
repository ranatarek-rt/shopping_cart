package com.dragon.shoppingCart.model;

import com.dragon.shoppingCart.entity.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;


}
