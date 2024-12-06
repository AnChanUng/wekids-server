package com.wekids.backend.auth.oauth2;

import com.wekids.backend.auth.dto.response.CustomOAuth2User;
import com.wekids.backend.auth.enums.LoginState;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
                response.sendRedirect("https://we-kids-fe-gold.vercel.app/signup/select");
            return;
        }

        String token = jwtUtil.createJwt(customUserDetails.getMemberId(), customUserDetails.getRole());
        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect("https://we-kids-fe-gold.vercel.app/");
    }

    private Cookie createCookie(String key, String value){
        try {
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            Cookie cookie = new Cookie(key, encodedValue);
            cookie.setMaxAge(60 * 60 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // HTTPS에서만 설정
            return cookie;
        }
        catch (Exception e){
            throw new WekidsException(ErrorCode.FAILED_COOKIE_ENCODING, "쿠키 인코딩 에러");
        }
    }
}
