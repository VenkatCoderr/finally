package com.example.socialmediaapp.responses;

import com.example.socialmediaapp.entities.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    public CommentResponse(Comment comment) {
		
	}

	private Long id;
    private Long userId;
    private String text;

    private String username;

   
}
