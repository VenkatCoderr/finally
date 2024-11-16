package com.example.socialmediaapp.services.impl;


import com.example.socialmediaapp.dataAccess.CommentRepository;
import com.example.socialmediaapp.entities.Comment;
import com.example.socialmediaapp.entities.Post;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.requests.CreateCommentRequest;
import com.example.socialmediaapp.requests.UpdateCommentRequest;
import com.example.socialmediaapp.responses.CommentResponse;
import com.example.socialmediaapp.responses.LikeResponse;
import com.example.socialmediaapp.services.CommentService;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private UserServiceImpl userService;
    private PostServiceImpl postService;

    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService, PostServiceImpl postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllComments(Optional<Long> userId, Optional<Long> postId) {
    	
        List<Comment> comments;
        
        if(userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get()); 
        }else if(userId.isPresent()){  
            comments =  commentRepository.findByUserId(userId.get());
        }else if (postId.isPresent()){  
            comments = commentRepository.findByPostId(postId.get());
        }else
            comments =  commentRepository.findAll();  
        
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CreateCommentRequest createCommentRequest) {
    	
        User user = userService.getUserById(createCommentRequest.getUserId());
        Post post = postService.getPostById(createCommentRequest.getPostId());
        
        if(user != null && post !=null) {
            Comment comment = new Comment();
            comment.setId(createCommentRequest.getId());
            comment.setPost(post);
            comment.setUser(user);
            comment.setText(createCommentRequest.getText());
            comment.setCreateDate(new Date());
            return commentRepository.save(comment);
        }
        return null;
    }

    public Comment updateCommentById(Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest) {
        Optional <Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment updateComment = comment.get();
            updateComment.setText(updateCommentRequest.getText());
            return commentRepository.save(updateComment);
        }else
            return null;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

