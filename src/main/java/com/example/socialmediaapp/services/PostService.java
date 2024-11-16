package com.example.socialmediaapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;

import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.requests.CreatePostRequest;
import com.example.socialmediaapp.requests.UpdatePostRequest;
import com.example.socialmediaapp.responses.PostResponse;

public interface PostService {

	
	List<PostResponse> getAllPosts(Optional<Long> userId);
	
	Post getPostById(Long postId);
	
	PostResponse getPostByIdWithLikes(Long postId);
	
	Post createPost(CreatePostRequest newPostRequest);
	
	Post updatePostById(Long postId, UpdatePostRequest updatePostRequest);
	
	void deletePostById(Long postId);
}
