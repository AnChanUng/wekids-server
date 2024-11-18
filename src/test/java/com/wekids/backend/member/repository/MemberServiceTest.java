package com.wekids.backend.member.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.dto.response.ParentResponse;
import com.wekids.backend.member.service.MemberServiceImpl;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import com.wekids.backend.support.fixture.DesignFixture;
import com.wekids.backend.support.fixture.ParentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private MemberRepository memberRepository;
    @InjectMocks private MemberServiceImpl memberService;

    @Test
    void 부모_자식_리스트를_조회한다(){

        Long parentId = 1L;

        Parent parent = new ParentFixture().build();
        Child child1 = new ChildFixture().id(1L).build();
        Child child2 = new ChildFixture().id(2L).build();

        // List<Child> 생성 및 추가
        List<Child> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);

        Account account = new AccountFixture().build();

        Design design = DesignFixture.builder().member(parent).account(account).build();

        Design childdesign = DesignFixture.builder().member(child1).account(account).build();

        given(memberRepository.findByIdAndMemberType(parentId)).willReturn(Optional.ofNullable(parent));

        given(accountRepository.findByMember(parent)).willReturn(Optional.ofNullable(account));

        given(accountRepository.findDesignByAccountId(parentId)).willReturn(design);

        ParentResponse parentResponse = ParentResponse.from(parent, account, design);

        given(memberRepository.findChildrenByParentId(parentId)).willReturn(children);

        Design wrongChildDesign = DesignFixture.builder().member(child2).account(account).character(CharacterType.DADAPING).build();
        given(accountRepository.findDesignByAccountId(child2.getId())).willReturn(wrongChildDesign);

        List<ChildResponse> childResponse = children.stream().map(child -> {
            given(accountRepository.findByMember(child)).willReturn(Optional.of(account));
            if (child.getId().equals(2L)) {
                return ChildResponse.from(child, account, wrongChildDesign);
            }
            return ChildResponse.from(child, account, childdesign);
        }).toList();


        assertThat(memberService.getParentAccount().getParent().getAccountId())
                .isEqualTo(new ParentAccountResponse(parentResponse, childResponse).getParent().getAccountId());
        assertThat(memberService.getParentAccount().getChildren().get(1).getAccountId())
                .isEqualTo(new ParentAccountResponse(parentResponse, childResponse).getChildren().get(1).getAccountId());
//        assertThat(memberService.getParentAccount().getChildren().get(0).getCharacter())
//                .isEqualTo(childResponse.get(1).getCharacter()); // 테스트 실패 코드

    }
}
