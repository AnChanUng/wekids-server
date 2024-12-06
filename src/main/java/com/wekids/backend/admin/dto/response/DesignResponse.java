package com.wekids.backend.admin.dto.response;

import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignResponse {
    private Long designId;
    private ColorType color;
    private CharacterType character;

    public static DesignResponse from(Design design) {
        return DesignResponse.builder()
                .designId(design.getMemberId())
                .color(design.getColor())
                .character(design.getCharacter())
                .build();
    }
}
