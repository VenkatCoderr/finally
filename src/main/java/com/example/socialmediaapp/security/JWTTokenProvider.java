package com.example.socialmediaapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.Date;

@Component
public class JWTTokenProvider {                                                     

    @Value("${question.app.secret}")                                             
    private String APP_SECRET = "socialMedia";

    @Value("${question.expires.in}")
    private long EXPIRES_IN;                                                        

    public String generateJWTToken(Authentication auth){                            
        JWTUserDetails userDetails = (JWTUserDetails) auth.getPrincipal();          
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);              
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,APP_SECRET).compact();       
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET).compact();
    }

    Long getUserIdFromJWT(String token){                                            
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());                                 
    }

    boolean validateToken(String token) {                                           
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);      
            return !isTokenExpired(token);                                          
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration(); 
        return expiration.before(new Date());                                                                           
    }


}
