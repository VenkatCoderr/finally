package com.example.socialmediaapp.services.impl;

import com.example.socialmediaapp.services.UserDetailsServiceImplementation;


import com.example.socialmediaapp.dataAccess.UserRepository;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.security.JWTUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementationImpl implements UserDetailsService,UserDetailsServiceImplementation { 

    private UserRepository userRepository;

    public UserDetailsServiceImplementationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return JWTUserDetails.create(user);  
    }

    public UserDetails loadUserById(Long id) { 
        User user = userRepository.findById(id).get();
        return JWTUserDetails.create(user);
    }

}
