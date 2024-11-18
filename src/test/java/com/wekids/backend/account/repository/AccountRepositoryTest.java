package com.wekids.backend.account.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MemberRepository memberRepository; // MemberRepository 모킹

    private Member childMember;
    private Member parentMember;

    @BeforeEach
    public void setUp() {
        // 아이 더미데이터 넣기
        childMember = new ChildFixture().build();
        parentMember = new ChildFixture()
                .name("강현우")
                .email("5678")
                .build();


        // 모킹된 memberRepository에서 member가 저장되면 실제 객체가 반환되도록 설정
//        when(memberRepository.save(any(Member.class))).thenReturn(childMember);
//        when(memberRepository.save(any(Member.class))).thenReturn(parentMember);
    }

    @Test
    public void 계좌번호로_자식_account_table에_있는_값_조회하기() {
        // Account 더미데이터 넣기
        Account childAccount = Account.builder()
                .accountNumber("CHILD1234567890")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build();

        // accountRepository에서 아이 accountNumber로 조회하면 해당 account를 반환하도록 설정
        when(accountRepository.findAccountByAccountNumber("CHILD1234567890")).thenReturn(Optional.of(childAccount));

        // When: Account를 accountNumber로 조회
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber("CHILD1234567890");

        // Then: 조회된 Account가 올바른지 확인
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getAccountNumber()).isEqualTo("CHILD1234567890");
        assertThat(foundAccount.get().getBalance()).isEqualTo(BigDecimal.valueOf(250.00));
        assertThat(foundAccount.get().getMember()).isEqualTo(childMember);

        // verify that the method was called once
        verify(accountRepository, times(1)).findAccountByAccountNumber("CHILD1234567890");
    }

    @Test
    public void 계좌번호로_부모_account_table에_있는_값_조회하기() {
        // Account 더미데이터 넣기
        Account parnetAccount = Account.builder()
                .accountNumber("PARENT1234567890")
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build();

        // accountRepository에서 부모 accountNumber로 조회하면 해당 account를 반환하도록 설정
        when(accountRepository.findAccountByAccountNumber("PARENT1234567890")).thenReturn(Optional.of(parnetAccount));

        // When: Account를 accountNumber로 조회
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber("PARENT1234567890");

        // Then: 조회된 Account가 올바른지 확인
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getAccountNumber()).isEqualTo("PARENT1234567890");
        assertThat(foundAccount.get().getBalance()).isEqualTo(BigDecimal.valueOf(500.00));
        assertThat(foundAccount.get().getMember()).isEqualTo(parentMember);

        // verify that the method was called once
        verify(accountRepository, times(1)).findAccountByAccountNumber("PARENT1234567890");
    }

    @Test
    public void 존재하지_않는_account_number로_조회를_한_경우() {
        // When: 존재하지 않는 accountNumber로 조회
        when(accountRepository.findAccountByAccountNumber("INVALID_ACCOUNT")).thenReturn(Optional.empty());

        // Then: Account가 존재하지 않으면 Optional.empty()가 반환됨.
        // TODO empty가 반환되는게 맞음? 아니면 여기도 wekidserror터쳐야됨?

        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber("INVALID_ACCOUNT");

        assertThat(foundAccount).isEmpty();

        // verify that the method was called once
        verify(accountRepository, times(1)).findAccountByAccountNumber("INVALID_ACCOUNT");
    }
}