package com.example.socialmediaapp.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsServiceImplementation {

	UserDetails loadUserByUsername(String username);
	
	UserDetails loadUserById(Long id);
}
