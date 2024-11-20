package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.SignUpRequest;
import com.wekids.backend.auth.enums.MemberType;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock JWTUtil jwtUtil;
    @Mock private MemberRepository memberRepository;
    @InjectMocks private AuthServiceImpl authService;

    @Test
    void 회원가입을_할_수_있다() {
        SignUpRequest request = new SignUpRequest(LocalDate.now(), "서동현", "010-0000-0000",
                "123456", "naver", MemberType.CHILD, "test@test.com");
        Member member = ChildFixture.builder().id(1L).build();
        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(false);
        given(memberRepository.save(any(Member.class))).willReturn(member);

        authService.signup(request);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void 이메일이_중복인_경우_회원가입을_할_수_없다() {
        SignUpRequest request = new SignUpRequest(LocalDate.now(), "서동현", "010-0000-0000",
                "123456", "naver", MemberType.CHILD, "test@test.com");
        Member member = ChildFixture.builder().id(1L).build();
        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(true);

        WekidsException exception = assertThrows(WekidsException.class, () -> {
            authService.signup(request);
        });

        assertEquals("test@test.com은 존재합니다", exception.getMessage());
        verify(memberRepository, times(0)).save(any(Member.class));
    }
}