package com.example.socialmediaapp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
@Table(name = "post")
public class Post {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)             
    @JoinColumn(name="user_id",nullable = false)    
    @OnDelete(action = OnDeleteAction.CASCADE)  
    private User user;

    private String title;

    @Lob                                   
    @Column(columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)   
                                        
    private Date createDate;
    
    


}
