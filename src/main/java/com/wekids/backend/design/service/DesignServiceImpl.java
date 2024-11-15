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
    public DesignResponse createDesign(Long memberId, DesignCreateRequest request) {

        Design newDesign = new Design();
        newDesign.setMemberId(memberId);

        newDesign.setColor(validateColorType(request.getColor()));
        newDesign.setCharacter(validateCharacterType(request.getCharacter()));

        designRepository.save(newDesign);
        return DesignResponse.of(newDesign.getColor().name(), newDesign.getCharacter().name());
    }

    // Enum 클래스의 모든 값 문자열 형태로 가져옴
    private <E extends Enum<E>> String getEnumValues(Class<E> enumClass) {
        return Arrays.toString(enumClass.getEnumConstants());
        // [RED, BLUE, GREEN]
    }
}