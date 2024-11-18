package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.dto.response.ParentResponse;
import com.wekids.backend.member.repository.MemberRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private DesignRepository designRepository;
    @InjectMocks private MemberServiceImpl memberService;

    @Test
    void 부모_자식_리스트를_조회한다(){

        Long parentId = 1L;

        Parent parent = ParentFixture.builder().build().from();
        Child child1 = new ChildFixture().id(1L).build();
        Child child2 = new ChildFixture().id(2L).build();

        // List<Child> 생성 및 추가
        List<Child> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);

        Account account = new AccountFixture().build();

        Design design = DesignFixture.builder().member(parent).account(account).build().from();

        Design childdesign = DesignFixture.builder().member(child1).account(account).build().from();

        given(memberRepository.findByIdAndMemberType(parentId)).willReturn(Optional.ofNullable(parent));

        given(accountRepository.findByMember(parent)).willReturn(Optional.ofNullable(account));

        given(designRepository.findByMemberId(parentId)).willReturn(design);

        ParentResponse parentResponse = ParentResponse.from(parent, account, design);

        given(memberRepository.findChildrenByParentId(parentId)).willReturn(Optional.of(children));



        List<ChildResponse> childResponse = children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            given(accountRepository.findByMember(child)).willReturn(Optional.of(account));
            given(designRepository.findByMemberId(child.getId())).willReturn(childdesign);
            return ChildResponse.from(child, account, childdesign);
        }).toList();


        assertThat(memberService.getParentAccount().getParent().getAccountId())
                .isEqualTo(new ParentAccountResponse(parentResponse, childResponse).getParent().getAccountId());
        assertThat(memberService.getParentAccount().getChildren().get(1).getAccountId())
                .isEqualTo(new ParentAccountResponse(parentResponse, childResponse).getChildren().get(1).getAccountId());


    }
}
