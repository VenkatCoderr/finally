package com.example.socialmediaapp.responses;

import com.example.socialmediaapp.entities.Like;
import lombok.Data;

@Data
public class LikeResponse { 
	
    public LikeResponse(Like like) {
		
	}
	private Long id;
    private Long userId;
    private Long postId;

   
}
