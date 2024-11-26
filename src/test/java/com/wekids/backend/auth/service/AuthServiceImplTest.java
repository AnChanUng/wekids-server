package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.request.SignUpRequest;
import com.wekids.backend.auth.dto.response.SignUpResponse;
import com.wekids.backend.auth.enums.MemberType;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.member.repository.ParentChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import com.wekids.backend.support.fixture.ParentChildFixture;
import com.wekids.backend.support.fixture.ParentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock PasswordEncoder passwordEncoder;
    @Mock JWTUtil jwtUtil;
    @Mock private MemberRepository memberRepository;
    @Mock private ParentRepository parentRepository;
    @Mock private ParentChildRepository parentChildRepository;
    @InjectMocks private AuthServiceImpl authService;

    @Test
    void 부모가_회원가입을_할_수_있다() {
        Parent parent = ParentFixture.builder().id(1L).build().parent();
        SignUpRequest request = SignUpRequest.builder()
                .birthday(parent.getBirthday())
                .name(parent.getName())
                .simplePassword(parent.getSimplePassword())
                .email(parent.getEmail())
                .phone(parent.getPhone())
                .memberType(MemberType.PARENT)
                .social("naver")
                .build();

        String expectedToken = "mocked-token";
        String encodedPassword = "encoded-password";

        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(false);
        given(passwordEncoder.encode(request.getSimplePassword())).willReturn(encodedPassword);
        given(jwtUtil.createJwt(anyLong(), anyString())).willReturn(expectedToken);
        given(memberRepository.save(any(Parent.class))).willReturn(parent);

        SignUpResponse signup = authService.signup(request);

        verify(memberRepository, times(1)).save(any(Parent.class));
        verify(passwordEncoder).encode(request.getSimplePassword());
        assertThat(signup.getToken()).isEqualTo(expectedToken);
    }

    @Test
    void 자식이_회원가입을_할_수_있다() {
        Parent parent = ParentFixture.builder().id(1L).build().parent();
        Child child = ChildFixture.builder().id(2L).build().child();
        ParentChild parentChild = ParentChildFixture.builder()
                .parent(parent)
                .parentId(parent.getId())
                .child(child)
                .childId(child.getId())
                .build()
                .parentChild();

        SignUpRequest request = SignUpRequest.builder()
                .birthday(child.getBirthday())
                .name(child.getName())
                .simplePassword(child.getSimplePassword())
                .email(child.getEmail())
                .phone(child.getPhone())
                .memberType(MemberType.CHILD)
                .social("naver")
                .guardianBirthday(parent.getBirthday())
                .guardianName(parent.getName())
                .guardianPhone(parent.getPhone())
                .build();
        String expectedToken = "mocked-token";
        String encodedPassword = "encoded-password";

        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(false);
        given(parentRepository.findByPhoneAndName(parent.getPhone(), parent.getName())).willReturn(Optional.of(parent));
        given(memberRepository.save(any(Child.class))).willReturn(child);
        given(parentChildRepository.save(any(ParentChild.class))).willReturn(parentChild);
        given(passwordEncoder.encode(request.getSimplePassword())).willReturn(encodedPassword);
        given(jwtUtil.createJwt(anyLong(), anyString())).willReturn(expectedToken);

        SignUpResponse signup = authService.signup(request);

        verify(memberRepository, times(1)).save(any(Child.class));
        verify(parentChildRepository, times(1)).save(any(ParentChild.class));
        verify(passwordEncoder).encode(request.getSimplePassword());
        assertThat(signup.getToken()).isEqualTo(expectedToken);
    }

    @Test
    void 이미_존재하는_이메일로_회원가입_시도할_경우_예외발생() {
        SignUpRequest request = SignUpRequest.builder()
                .email("test@example.com")
                .simplePassword("password123")
                .name("Test User")
                .birthday(LocalDate.of(2000, 1, 1))
                .phone("010-1234-5678")
                .memberType(MemberType.PARENT)
                .build();

        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(true);

        assertThrows(WekidsException.class, () -> {
            authService.signup(request);
        });
    }

    @Test
    void 부모의_나이가_19세_미만일_경우_예외발생() {
        SignUpRequest request = SignUpRequest.builder()
                .birthday(LocalDate.now())
                .name("Young Parent")
                .simplePassword("password123")
                .email("young@example.com")
                .phone("010-1234-5678")
                .memberType(MemberType.PARENT)
                .build();

        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(false);

        assertThrows(WekidsException.class, () -> {
            authService.signup(request);
        });
    }

    @Test
    void 자식의_나이가_14세_이상일_경우_예외발생() {
        Child child = ChildFixture.builder().id(1L).build().child();
        SignUpRequest request = SignUpRequest.builder()
                .birthday(LocalDate.now().minusYears(14))
                .name("Old Child")
                .simplePassword("password123")
                .email("old@example.com")
                .phone("010-1234-5678")
                .memberType(MemberType.CHILD)
                .guardianBirthday(child.getBirthday())
                .guardianName(child.getName())
                .guardianPhone(child.getPhone())
                .build();

        given(memberRepository.existsAllByEmail(request.getEmail())).willReturn(false);

        assertThrows(WekidsException.class, () -> {
            authService.signup(request);
        });
    }
}
