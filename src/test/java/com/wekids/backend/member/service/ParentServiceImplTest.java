package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import com.wekids.backend.support.fixture.DesignFixture;
import com.wekids.backend.support.fixture.ParentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ParentServiceImplTest {

    @Mock private AccountRepository accountRepository;
    @Mock private ParentRepository parentRepository;
    @Mock private ChildRepository childRepository;
    @Mock private DesignRepository designRepository;
    @InjectMocks private ParentServiceImpl parentService;

    @Test
    void 부모_자식_리스트를_조회한다() {
        // Parent 및 Child 객체 생성
        Parent parent = ParentFixture.builder().id(1L).build().parent();
        Child child1 = ChildFixture.builder().id(2L).build().child();
        Child child2 = ChildFixture.builder().id(3L).build().child();
        List<Child> children = List.of(child1, child2);

        // Account 객체 생성
        Account parentAccount = AccountFixture.builder().id(1L).member(parent).build().account();
        Account childAccount1 = AccountFixture.builder().id(2L).member(child1).build().account();
        Account childAccount2 = AccountFixture.builder().id(3L).member(child2).build().account();

        // Design 객체 생성
        Design parentDesign = DesignFixture.builder().memberId(1L).member(parent).account(parentAccount).build().design();
        Design childDesign1 = DesignFixture.builder().memberId(2L).member(child1).account(childAccount1).build().design();
        Design childDesign2 = DesignFixture.builder().memberId(3L).member(child2).account(childAccount2).build().design();

        // Mock 동작 정의
        given(parentRepository.findById(parent.getId())).willReturn(Optional.of(parent));
        given(childRepository.findChildrenByParentId(parent.getId())).willReturn(children);

        given(accountRepository.findByMember(parent)).willReturn(Optional.of(parentAccount));
        given(accountRepository.findByMember(child1)).willReturn(Optional.of(childAccount1));
        given(accountRepository.findByMember(child2)).willReturn(Optional.of(childAccount2));

        given(designRepository.findById(parent.getId())).willReturn(Optional.of(parentDesign));
        given(designRepository.findById(child1.getId())).willReturn(Optional.of(childDesign1));
        given(designRepository.findById(child2.getId())).willReturn(Optional.of(childDesign2));

        // 서비스 호출
        ParentAccountResponse response = parentService.showParentAccount(parent.getId());

        // 검증
        assertThat(response).isNotNull();
        assertThat(response.getParent()).isNotNull();
        assertThat(response.getParent().getName()).isEqualTo(parent.getName());
        assertThat(response.getParent().getAccountNumber()).isEqualTo(parentAccount.getAccountNumber());
        assertThat(response.getParent().getColor()).isEqualTo(parentDesign.getColor());
        assertThat(response.getChildren()).hasSize(2);

        // 자식 응답 검증
        ChildResponse childResponse1 = response.getChildren().get(0);
        assertThat(childResponse1.getChildId()).isEqualTo(child1.getId());
        assertThat(childResponse1.getName()).isEqualTo(child1.getName());
        assertThat(childResponse1.getAccountNumber()).isEqualTo(childAccount1.getAccountNumber());
        assertThat(childResponse1.getColor()).isEqualTo(childDesign1.getColor());

        ChildResponse childResponse2 = response.getChildren().get(1);
        assertThat(childResponse2.getChildId()).isEqualTo(child2.getId());
        assertThat(childResponse2.getName()).isEqualTo(child2.getName());
        assertThat(childResponse2.getAccountNumber()).isEqualTo(childAccount2.getAccountNumber());
        assertThat(childResponse2.getColor()).isEqualTo(childDesign2.getColor());
    }

    @Test
    void 부모_계좌만_존재하고_자식이_없다() {
        // Parent 객체 생성
        Parent parent = ParentFixture.builder().id(1L).build().parent();

        // Account 객체 생성
        Account parentAccount = AccountFixture.builder().id(1L).member(parent).build().account();

        // Design 객체 생성
        Design parentDesign = DesignFixture.builder().memberId(1L).member(parent).account(parentAccount).build().design();

        // Mock 동작 정의
        given(parentRepository.findById(parent.getId())).willReturn(Optional.of(parent));
        given(childRepository.findChildrenByParentId(parent.getId())).willReturn(List.of());
        given(accountRepository.findByMember(parent)).willReturn(Optional.of(parentAccount));
        given(designRepository.findById(parent.getId())).willReturn(Optional.of(parentDesign));

        // 서비스 호출
        ParentAccountResponse response = parentService.showParentAccount(parent.getId());

        // 검증
        assertThat(response).isNotNull();
        assertThat(response.getParent()).isNotNull();
        assertThat(response.getParent().getName()).isEqualTo(parent.getName());
        assertThat(response.getChildren()).isEmpty(); // 자식이 없음을 검증
    }

    @Test
    void 부모_정보를_찾을_수_없다() {
        Long parentId = 1L;

        // Mock 동작 정의
        given(parentRepository.findById(parentId))
                .willThrow(new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가 " + parentId + "인 정보를 찾을 수 없습니다."));

        // 예외 검증
        assertThatThrownBy(() -> parentService.showParentAccount(parentId))
                .isInstanceOf(WekidsException.class)
                .hasMessageContaining("parentId가 " + parentId + "인 정보를 찾을 수 없습니다.");
    }

    @Test
    void 자식_계좌나_디자인_정보가_없는_경우() {
        // Parent 및 Child 객체 생성
        Parent parent = ParentFixture.builder().id(1L).build().parent();
        Child child = ChildFixture.builder().id(2L).build().child();
        List<Child> children = List.of(child);

        // Account 및 Design 객체 생성
        Account parentAccount = AccountFixture.builder().id(1L).member(parent).build().account();
        Design parentDesign = DesignFixture.builder().memberId(1L).member(parent).account(parentAccount).build().design();

        // Mock 동작 정의
        given(parentRepository.findById(parent.getId())).willReturn(Optional.of(parent));
        given(childRepository.findChildrenByParentId(parent.getId())).willReturn(children);
        given(accountRepository.findByMember(parent)).willReturn(Optional.of(parentAccount));
        given(designRepository.findById(parent.getId())).willReturn(Optional.of(parentDesign));
        given(accountRepository.findByMember(child)).willReturn(Optional.empty()); // 자식 계좌 없음
        given(designRepository.findById(child.getId())).willReturn(Optional.empty()); // 자식 디자인 없음

        // 서비스 호출
        ParentAccountResponse response = parentService.showParentAccount(parent.getId());

        // 검증
        assertThat(response).isNotNull();
        assertThat(response.getChildren()).hasSize(1);

        ChildResponse childResponse = response.getChildren().get(0);
        assertThat(childResponse.getChildId()).isEqualTo(child.getId());
        assertThat(childResponse.getAccountNumber()).isNull(); // 계좌 정보 없음
        assertThat(childResponse.getColor()).isNull(); // 디자인 정보 없음
    }

    @Test
    void 부모와_자식_정보가_모두_없는_경우() {
        Long parentId = 1L;

        // Mock 동작 정의
        given(parentRepository.findById(parentId)).willReturn(Optional.empty());

        // 예외 검증
        assertThatThrownBy(() -> parentService.showParentAccount(parentId))
                .isInstanceOf(WekidsException.class)
                .hasMessageContaining("parentId가 " + parentId + "인 정보를 찾을 수 없습니다.");
    }

}

