package com.example.socialmediaapp.controllers;


import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.requests.CreateLikeRequest;
import com.example.socialmediaapp.responses.LikeResponse;
import com.example.socialmediaapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId) {
        return likeService.getAllLikes(userId, postId);
    }

    @PostMapping
    public Like createLike(@RequestBody CreateLikeRequest createLikeRequest) {
        return likeService.createLike(createLikeRequest);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId) {
        return likeService.getLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLikeById(@PathVariable Long likeId) {
        likeService.deleteLikeById(likeId);
    }
}
