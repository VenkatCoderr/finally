package com.example.socialmediaapp.services;

import java.util.List;
import java.util.Optional;

import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.requests.CreateLikeRequest;
import com.example.socialmediaapp.responses.LikeResponse;

public interface LikeService {

	 List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId);
	 
	 Like getLikeById(Long LikeId);
	 
	 Like createLike(CreateLikeRequest CreateLikeRequest);
	 
	 void deleteLikeById(Long likeId); 
}
