package com.blog.blogWeb.Controllers;


import org.hibernate.annotations.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.blog.blogWeb.Repositories.UserRepositories;
import com.blog.blogWeb.Services.UserService;
import com.blog.blogWeb.dto.UserLoginResqDto;
import com.blog.blogWeb.dto.UserResqDto;
import com.blog.blogWeb.entity.User;
import com.blog.blogWeb.exception.UserNotFoundException;
import com.blog.blogWeb.security.JwtProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserResqDto userReq) throws UserNotFoundException {
       userService.registerUser(userReq);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginResqDto loginRequest) {
    	
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
    	

        String token = jwtProvider.generateToken(loginRequest.username());
        

        return ResponseEntity.ok(token);
    }
}
