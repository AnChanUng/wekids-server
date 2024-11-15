package com.wekids.backend.design.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DesignCreateRequest {
    private String color;
    private String character;
}
