package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.request.SignUpRequest;
import com.wekids.backend.auth.dto.response.SignUpResponse;
import com.wekids.backend.auth.enums.MemberType;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wekids.backend.exception.ErrorCode.EMAIL_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        validateAlreadyExistEmail(signUpRequest);
        String encode = passwordEncoder.encode(signUpRequest.getSimplePassword());
        Member member = (signUpRequest.getMemberType().equals(MemberType.PARENT)) ?
                signUpRequest.toParent(encode) : signUpRequest.toChild(encode);
        Long memberId = memberRepository.save(member).getId();
        return SignUpResponse.from(jwtUtil.createJwt(memberId, "ROLE_"+MemberType.PARENT));
    }

    private void validateAlreadyExistEmail(SignUpRequest request){
        if(memberRepository.existsAllByEmail(request.getEmail())){
            throw new WekidsException(EMAIL_ALREADY_EXIST, request.getEmail() + "은 존재합니다");
        }
    }
}
