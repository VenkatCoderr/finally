package com.example.socialmediaapp.services;

import java.util.List;

import com.example.socialmediaapp.entities.User;

public interface UserService {

	List<User> getAllUsers();
	
	User createUser(User user);
	
	User getUserById(Long userId);
	
	User updateUserById(Long userId, User newUser);
	
	void deleteUserById(Long userId);
	 
	User getUserByUsername(String username) ;
	 
	List<Object> getUserActivityById(Long userId); 
}
