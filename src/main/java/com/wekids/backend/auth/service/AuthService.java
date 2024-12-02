package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.request.SignUpRequest;
import com.wekids.backend.auth.dto.response.SignUpResponse;

public interface AuthService {
    SignUpResponse signup(SignUpRequest signUpRequest);
}
