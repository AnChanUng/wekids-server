package com.wekids.backend.design.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignCreateRequest {
    private String color;
    private String character;
}
