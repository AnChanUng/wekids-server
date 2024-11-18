package com.wekids.backend.design.service;

import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DesignServiceImpl implements DesignService {

    private final DesignRepository designRepository;

    @Override
    public DesignResponse showDesign(Long memberId) {
        Design design = findDesignByMemberId(memberId);
        return DesignResponse.of(design.getColor().name(), design.getCharacter().name());
    }

    @Override
    @Transactional
    public void createDesign(Long memberId, DesignCreateRequest request) {

        ColorType color = ColorType.valueOf(request.getColor());
        CharacterType character = CharacterType.valueOf(request.getCharacter());

        Design newDesign = Design.builder()
                .memberId(memberId)
                .color(color)
                .character(character)
                .build();

        designRepository.save(newDesign);
    }

    private Design findDesignByMemberId(Long memberId) {
        return designRepository.findById(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.DESIGN_NOT_FOUND, "디자인을 찾을 수 없습니다."));
    }

    private ColorType validateColorType(String color) {
        try {
            return ColorType.valueOf(color);
        } catch (IllegalArgumentException e) {
            throw new WekidsException(ErrorCode.INVALID_INPUT, "잘못된 컬러 타입입니다.");
        }
    }

    private CharacterType validateCharacterType(String character) {
        try {
            return CharacterType.valueOf(character);
        } catch (IllegalArgumentException e) {
            throw new WekidsException(ErrorCode.INVALID_INPUT, "잘못된 캐릭터 타입입니다.");
        }
    }
}