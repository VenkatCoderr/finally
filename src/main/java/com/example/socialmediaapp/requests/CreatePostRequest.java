package com.example.socialmediaapp.requests;
import lombok.Data;

@Data
public class CreatePostRequest {
	
	private Long id;
    private String text;
    private String title;
    private Long userId;
    
  }
