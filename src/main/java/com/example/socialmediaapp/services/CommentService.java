package com.example.socialmediaapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.socialmediaapp.entities.Comment;
import com.example.socialmediaapp.requests.CreateCommentRequest;
import com.example.socialmediaapp.requests.UpdateCommentRequest;
import com.example.socialmediaapp.responses.CommentResponse;

public interface CommentService {
        
	 List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId);
	
	 Comment getCommentById(Long commentId);
	
     Comment createComment(CreateCommentRequest createCommentRequest);
	
	 Comment updateCommentById(Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest);
	
	 void deleteCommentById(Long commentId);
	
}
