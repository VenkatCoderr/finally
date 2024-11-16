package com.example.socialmediaapp.controllers;

import com.example.socialmediaapp.dataAccess.UserRepository;
import com.example.socialmediaapp.entities.RefreshToken;
import com.example.socialmediaapp.entities.User;
import com.example.socialmediaapp.requests.RefreshTokenRequest;
import com.example.socialmediaapp.requests.UserRequest;
import com.example.socialmediaapp.responses.AuthenticationResponse;
import com.example.socialmediaapp.security.JWTTokenProvider;
import com.example.socialmediaapp.services.RefreshTokenService;
import com.example.socialmediaapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
	private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJWTToken(auth); 
        User user = userService.getUserByUsername(loginRequest.getUsername());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken("Bearer " + jwtToken);
        authenticationResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authenticationResponse.setUserId(user.getId());
        return authenticationResponse;
    }
  
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequest registerRequest){
    	
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if(userService.getUserByUsername(registerRequest.getUsername()) != null){           
            authenticationResponse.setMessage("Username already taken.");
            return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.createUser(user);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerRequest.getUsername(),registerRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJWTToken(auth);

        authenticationResponse.setMessage("User Successfully Registered");
        authenticationResponse.setAccessToken("Bearer "+jwtToken);
        authenticationResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authenticationResponse.setUserId(user.getId());
        return new ResponseEntity<>(authenticationResponse,HttpStatus.CREATED);
    }

    @PostMapping("/refresh") 
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse authResponse = new AuthenticationResponse(); 
        RefreshToken token = refreshTokenService.getByUser(refreshTokenRequest.getUserId());
        if(token.getToken().equals(refreshTokenRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {
            User user = token.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId());  
            authResponse.setMessage("token successfully refreshed.");
            authResponse.setAccessToken("Bearer " + jwtToken);
            authResponse.setUserId(user.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            authResponse.setMessage("refresh token is not valid.");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }

    }
}
