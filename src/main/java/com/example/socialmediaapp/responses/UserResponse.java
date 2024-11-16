package com.example.socialmediaapp.responses;


import com.example.socialmediaapp.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    public UserResponse(User user) {
		
	}
	private Long id;
    private int imageId;
    private String username;

}
