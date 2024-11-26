package com.dragon.shoppingCart.model;
import com.dragon.shoppingCart.entity.Category;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ProductDto {
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventoryQuantity;
    private Category category;
    private List<ImageDto> images;
}
