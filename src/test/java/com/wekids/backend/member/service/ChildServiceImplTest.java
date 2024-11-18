package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wekids.backend.design.domain.enums.ColorType;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChildServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(ChildServiceImplTest.class);

    @Mock
    private ChildRepository childRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DesignRepository designRepository;

    @InjectMocks
    private ChildServiceImpl childService;

    @Test
    void 자식정보_childId로_조회한다() {
        Long childId = 2L;

        Child child = ChildFixture.builder()
                .id(childId)
                .cardState(CardState.NONE)
                .build()
                .build();

        Account childAccount = Account.builder().build();
        Design design = Design.builder().color(ColorType.PINK1).character(CharacterType.HEARTSPRING).build();

        when(childRepository.findById(childId)).thenReturn(Optional.of(child));
        when(accountRepository.findByMember(child)).thenReturn(Optional.of(childAccount));
        when(designRepository.findById(childId)).thenReturn(Optional.of(design));

        ChildResponse foundChild = childService.getChildAccount();

        assertThat(foundChild).isNotNull();
        assertThat(foundChild.getChildId()).isEqualTo(childId);

    }

}
