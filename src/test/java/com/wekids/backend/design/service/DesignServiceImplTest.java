package com.wekids.backend.design.service;

import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.ChildFixture;
import com.wekids.backend.support.fixture.DesignFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DesignServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(DesignServiceImplTest.class);

    @Mock
    private DesignRepository designRepository;

    @InjectMocks
    private DesignServiceImpl designService;

    @Test
    void 디자인_뷰_memberId로_조회한다() {
        Long memberId = 2L;

        Member member = ChildFixture.builder()
                .id(memberId)
                .build();

        Design design = DesignFixture.builder()
                .withMemberId(memberId)
                .withMember(member)
                .build();

        when(designRepository.findById(memberId)).thenReturn(Optional.of(design));

        DesignResponse foundDesign = designService.showDesign(memberId);

        assertThat(foundDesign.getColor()).isEqualTo(design.getColor().name());
        assertThat(foundDesign.getCharacter()).isEqualTo(design.getCharacter().name());
    }

    @Test
    void 디자인을_저장한다() {
        Long memberId = 2L;
        DesignCreateRequest request = new DesignCreateRequest();
        request.setColor("PINK1");
        request.setCharacter("HEARTSPRING");

        Design design = DesignFixture.builder()
                .withMemberId(memberId)
                .withColor(ColorType.valueOf(request.getColor()))
                .withCharacter(CharacterType.valueOf(request.getCharacter()))
                .build();

        when(designRepository.save(any(Design.class))).thenReturn(design);
        DesignResponse createdDesign = designService.createDesign(memberId, request);

        assertThat(createdDesign.getColor()).isEqualTo(design.getColor().name());
        assertThat(createdDesign.getCharacter()).isEqualTo(design.getCharacter().name());
    }
}
