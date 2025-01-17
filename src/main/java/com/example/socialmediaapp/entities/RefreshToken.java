package com.example.socialmediaapp.entities;


import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="refresh_token")
@Data
public class RefreshToken {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date expiryDate;  

	@Column(nullable = false, unique = true)
    String token;
    
}