package com.example.socialmediaapp.responses;

import lombok.Data;

@Data
public class AuthenticationResponse {

    String message;
    Long userId;
    String refreshToken;
    String AccessToken;
    
   }

