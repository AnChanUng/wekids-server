package com.wekids.backend.auth.oauth2;

import com.wekids.backend.auth.dto.response.CustomOAuth2User;
import com.wekids.backend.auth.enums.LoginState;
import com.wekids.backend.auth.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        LoginState loginState = customUserDetails.getLoginState();

        if(loginState.equals(LoginState.JOIN)){
            response.addCookie(createCookie("name", customUserDetails.getName()));
            response.addCookie(createCookie("email", customUserDetails.getEmail()));
            response.addCookie(createCookie("birthday", customUserDetails.getBirthday()));
                response.sendRedirect("http://localhost:3000/select");
            return;
        }

        String token = jwtUtil.createJwt(customUserDetails.getMemberId(), customUserDetails.getRole());
        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
//        cookie.setSecure(true); //https에서만 설정할 수 있도록
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
