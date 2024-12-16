package com.dragon.shoppingCart;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@OpenAPIDefinition(
		info = @Info(
				title = "Shopping Cart API",
				version = "v1.0",
				description = "This is the API documentation for the Shopping Cart application",
				contact = @Contact(name = "Rana Tarek", email = "ranatarek138@gmail.com")
		),
		externalDocs = @ExternalDocumentation(
				description = "Find more info here",
				url = "https://github.com/ranatarek-rt/shopping_cart"
		)
)

@SpringBootApplication
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

}
