package com.dragon.shoppingCart.security.jwt;
import com.dragon.shoppingCart.security.ShoppingUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;



@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecretKey}")
    private String jwtSecretKey;
    @Value("${auth.token.expirationTime}")
    private int expirationTime;

    public String generateUserToken(Authentication authentication){
        ShoppingUserDetails userDetails = (ShoppingUserDetails) authentication.getPrincipal();
        List<String> userRoles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder().subject(userDetails.getEmail())
                .claim("id",userDetails.getId())
                .claim("roles",userRoles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key())
                .compact();
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    public String getUserNameFromToken(String token){

        return Jwts.parser()
                .verifyWith(key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }

    }

}
