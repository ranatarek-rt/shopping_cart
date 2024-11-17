package com.dragon.shoppingCart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;

}
