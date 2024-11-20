package com.wekids.backend.auth.jwt;

import com.wekids.backend.auth.dto.response.CustomOAuth2User;
import com.wekids.backend.auth.enums.LoginState;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("Authorization")){
                authorization = cookie.getValue();
            }
        }

        if(authorization == null){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;
        if(jwtUtil.isExpired(token)){
            filterChain.doFilter(request, response);
            return;
        }

        Long memberId = jwtUtil.getMemberId(token);
        String role = jwtUtil.getRole(token);
        CustomOAuth2User customOAuth2User = CustomOAuth2User.of(LoginState.LOGIN, role, memberId);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
