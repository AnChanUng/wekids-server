package com.wekids.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {
    private String token;

    public static SignUpResponse from(String token){
        return new SignUpResponse(token);
    }
}
