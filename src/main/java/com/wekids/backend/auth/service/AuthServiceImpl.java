package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.request.SignUpRequest;
import com.wekids.backend.auth.dto.response.SignUpResponse;
import com.wekids.backend.auth.enums.MemberType;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.member.repository.ParentChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

import static com.wekids.backend.exception.ErrorCode.EMAIL_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ParentChildRepository parentChildRepository;
    private final ParentRepository parentRepository;
    private final JWTUtil jwtUtil;

    @Override
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        validateAlreadyExistEmail(signUpRequest);

        return signUpRequest.getMemberType().equals(MemberType.PARENT)
                ? signupParent(signUpRequest)
                : signupChild(signUpRequest);
    }

    private void validateAlreadyExistEmail(SignUpRequest request){
        if(memberRepository.existsAllByEmail(request.getEmail())){
            throw new WekidsException(EMAIL_ALREADY_EXIST, request.getEmail() + "은 존재합니다");
        }
    }

    private SignUpResponse signupParent(SignUpRequest request){
        validateAdult(request.getBirthday());

        String encode = passwordEncoder.encode(request.getSimplePassword());
        Parent parent = request.toParent(encode, request);

        Long memberId = memberRepository.save(parent).getId();

        return SignUpResponse.from(jwtUtil.createJwt(memberId, "ROLE_"+MemberType.PARENT));
    }

    public void validateAdult(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthdate, today);
        if (age.getYears() < 19) {
            throw new WekidsException(ErrorCode.INVALID_SIGNUP_AGE, "현재 나이는 " + age.getYears() + "입니다. 19세 이상이어야 합니다");
        }
    }


    private SignUpResponse signupChild(SignUpRequest request){
        validateUnderFourteen(request.getBirthday());

        String encode = passwordEncoder.encode(request.getSimplePassword());
        Parent parent = findParentByPhoneAndName(request.getGuardianPhone(), request.getGuardianName());
        Child child = request.toChild(encode, request);
        ParentChild parentChild = ParentChild.of(parent, child);

        Long memberId = memberRepository.save(child).getId();
        parentChildRepository.save(parentChild);

        return SignUpResponse.from(jwtUtil.createJwt(memberId, "ROLE_"+MemberType.CHILD));
    }

    public void validateUnderFourteen(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthdate, today);
        if (age.getYears() >= 14) {
            throw new WekidsException(ErrorCode.INVALID_SIGNUP_AGE, "현재 나이는 " + age.getYears() + "입니다. 14세 미만이어야 합니다.");
        }
    }

    private Parent findParentByPhoneAndName(String phone, String name){
        return parentRepository.findByPhoneAndName(phone, name).orElseThrow(()
                -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, phone + "의 전화번호를 가진 법정대리인은 Wekids에 부모 계정으로 회원가입하지 않았습니다."));
    }
}
