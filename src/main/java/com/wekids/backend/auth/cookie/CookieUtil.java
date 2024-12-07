package com.wekids.backend.auth.cookie;


import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {

        try{
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
            ResponseCookie cookie = ResponseCookie.from(name, encodedValue)
                    .path("/")
                    .sameSite("None")
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(60 * 60 * 60)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
         }
        catch (Exception e){
            throw new WekidsException(ErrorCode.FAILED_COOKIE_ENCODING, "쿠키 인코딩 에러");
         }
    }
}