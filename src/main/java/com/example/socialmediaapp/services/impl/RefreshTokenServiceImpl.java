package com.example.socialmediaapp.services.impl;

import com.example.socialmediaapp.services.RefreshTokenService;

import com.example.socialmediaapp.dataAccess.RefreshTokenRepository;
import com.example.socialmediaapp.entities.RefreshToken;
import com.example.socialmediaapp.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user) {
        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        
        if(token == null) {
            token =	new RefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());  
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds))); 
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public boolean isRefreshExpired(RefreshToken token) { 
        return token.getExpiryDate().before(new Date());
    }

}