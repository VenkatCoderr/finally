package com.example.socialmediaapp.dataAccess;

import com.example.socialmediaapp.entities.Comment;
import com.example.socialmediaapp.entities.Like;
import com.example.socialmediaapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {


    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

    @Query(value = 	"select 'liked', l.post_id, u.image, u.username from "
    		
            + "post_like l left join user u on u.id = l.user_id "
            
            + "where l.post_id in :postIds limit 5", nativeQuery = true)
    
    List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);
}


  