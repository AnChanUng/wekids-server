package com.wekids.backend.design.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DesignResponse {
    private String color;
    private String character;

    public static DesignResponse of(String color, String character){
        return new DesignResponse(color, character);
    }
}
