package com.example.socialmediaapp.services;

import com.example.socialmediaapp.entities.RefreshToken;
import com.example.socialmediaapp.entities.User;

public interface RefreshTokenService {

	 String createRefreshToken(User user);
	 RefreshToken getByUser(Long userId);
	 boolean isRefreshExpired(RefreshToken token);
}
