package com.example.socialmediaapp.security;

import com.example.socialmediaapp.services.UserDetailsServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter { 

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwtToken = extractJwtFromRequest(request);                                          
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {            
                Long id = jwtTokenProvider.getUserIdFromJWT(jwtToken);                                 
                UserDetails user = userDetailsServiceImplementation.loadUserById(id);                   
                if(user != null){                                                                       
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());  
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);                          
                }
            }
        }catch(Exception e) {
            return;
        }
        filterChain.doFilter(request, response);                                                           
    }

    private String extractJwtFromRequest(HttpServletRequest request) {                                      
        String bearer = request.getHeader("Authorization");                                              
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){                                   
            return bearer.substring("Bearer".length()+1);                                          
        }
        return null;
    }

}
