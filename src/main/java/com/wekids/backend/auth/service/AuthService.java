package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.SignUpRequest;
import com.wekids.backend.auth.dto.SignUpResponse;

public interface AuthService {
    SignUpResponse signup(SignUpRequest signUpRequest);
}
