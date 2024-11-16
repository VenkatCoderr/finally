package com.example.socialmediaapp.services.impl;
import com.example.socialmediaapp.services.LikeService;

import com.example.socialmediaapp.dataAccess.CommentRepository;
import com.example.socialmediaapp.dataAccess.LikeRepository;
import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.requests.CreateLikeRequest;
import com.example.socialmediaapp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {
    private LikeRepository likeRepository;
    private UserServiceImpl userService;
    private PostServiceImpl postService;

    public LikeServiceImpl(LikeRepository likeRepository, UserServiceImpl userService, PostServiceImpl postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
    	          
        List<Like> list;
        
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();                                    
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList()); 
    }

    public Like getLikeById(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public Like createLike(CreateLikeRequest CreateLikeRequest) {
        User user = userService.getUserById(CreateLikeRequest.getUserId());
        Post post = postService.getPostById(CreateLikeRequest.getPostId());
        if(user != null && post != null) {
            Like like = new Like();
            like.setId(CreateLikeRequest.getId());
            like.setPost(post);
            like.setUser(user);
            return likeRepository.save(like);
        }else
            return null;
    }

    public void deleteLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}

