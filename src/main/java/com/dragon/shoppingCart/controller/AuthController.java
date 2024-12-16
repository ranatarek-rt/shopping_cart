package com.dragon.shoppingCart.controller;


import com.dragon.shoppingCart.request.LoginRequest;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.response.JwtResponse;
import com.dragon.shoppingCart.security.ShoppingUserDetails;
import com.dragon.shoppingCart.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api/auth"))
public class AuthController {
    AuthenticationManager authManager;
    JwtUtils jwtUtils;

    @Autowired
    AuthController(AuthenticationManager authManager,JwtUtils jwtUtils){
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication auth  = authManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken
                                    (loginRequest.getEmail(),loginRequest.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtUtils.generateUserToken(auth);
            ShoppingUserDetails  userDetails = (ShoppingUserDetails) auth.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(),token);
            return ResponseEntity.ok(new ApiResponse("the user is successfully logged in",jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("invalid email or password try again",null));
        }
    }
}
