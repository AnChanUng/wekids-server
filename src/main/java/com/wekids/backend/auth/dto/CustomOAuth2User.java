package com.wekids.backend.auth.dto;

import com.wekids.backend.auth.enums.LoginState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Builder
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private LoginState loginState;
    private String role;
    private Long memberId;
    private String name;
    private String email;

    public static CustomOAuth2User of(LoginState loginState, String name, String email){
        return CustomOAuth2User.builder()
                .loginState(loginState)
                .name(name)
                .email(email)
                .build();
    }

    public static CustomOAuth2User of(LoginState loginState, String role, Long memberId){
        return CustomOAuth2User.builder()
                .loginState(loginState)
                .role(role)
                .memberId(memberId)
                .name("none")
                .build();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return name;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public String getRole() {
        return role;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

}
