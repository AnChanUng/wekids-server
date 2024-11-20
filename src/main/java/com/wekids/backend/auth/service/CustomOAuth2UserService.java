package com.wekids.backend.auth.service;

import com.wekids.backend.auth.dto.response.CustomOAuth2User;
import com.wekids.backend.auth.dto.response.NaverResponse;
import com.wekids.backend.auth.dto.response.OAuth2Response;
import com.wekids.backend.auth.enums.LoginState;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
http://localhost:8080/oauth2/authorization/naver
* */

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("naver")) {oAuth2Response = new NaverResponse(oAuth2User.getAttributes());}
        else return null;

        Optional<Member> member = memberRepository.findByEmail(oAuth2Response.getEmail());

        if(member.isEmpty()){
            return CustomOAuth2User.of(LoginState.JOIN, oAuth2Response.getName(), oAuth2Response.getEmail(), oAuth2Response.getBirthday().toString());
        }

        member.get().updateSocialInfo(oAuth2Response.getName(), oAuth2Response.getPhone(),
                oAuth2Response.getEmail(),oAuth2Response.getBirthday(), oAuth2Response.getProfile());
        Long memberId = member.get().getId();
        String role = member.get().getRole();
        return  CustomOAuth2User.of(LoginState.LOGIN, role, memberId);
    }
}
