package com.example.springbootfirst.controllers;

import com.example.springbootfirst.jwt.JwtTokenProvider;
import com.example.springbootfirst.models.LoginDetails;
import com.example.springbootfirst.models.UserDetailsDto;
import com.example.springbootfirst.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class RegisterController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public String register(@RequestBody UserDetailsDto request){
        return registerService.registerNewUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDetails request) {
        System.out.println("🔑 Attempting login for: " + request.getUserName());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println("✅ JWT Token issued");
        return ResponseEntity.ok(token);
    }



}
