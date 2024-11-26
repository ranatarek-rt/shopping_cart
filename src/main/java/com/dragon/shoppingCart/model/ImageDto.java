package com.dragon.shoppingCart.model;
import lombok.*;

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
