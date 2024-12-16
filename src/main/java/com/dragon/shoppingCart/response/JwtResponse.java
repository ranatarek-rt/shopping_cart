package com.dragon.shoppingCart.response;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class JwtResponse {
    private Long id;
    private String token;

}
