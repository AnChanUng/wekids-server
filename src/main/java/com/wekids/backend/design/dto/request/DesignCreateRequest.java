package com.wekids.backend.design.dto.request;

import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DesignCreateRequest {
    private ColorType color;
    private CharacterType character;
}
