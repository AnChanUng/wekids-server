package com.wekids.backend.auth.controller;

import com.wekids.backend.auth.dto.request.SignUpRequest;
import com.wekids.backend.auth.dto.response.SignUpResponse;
import com.wekids.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/v1/signup")
    public ResponseEntity<SignUpResponse> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        SignUpResponse response = authService.signup(signUpRequest);
        return new ResponseEntity<>(response, CREATED);
    }
}
