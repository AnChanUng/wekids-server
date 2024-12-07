package com.wekids.backend.config;

import com.wekids.backend.auth.jwt.JWTFilter;
import com.wekids.backend.auth.jwt.JWTUtil;
import com.wekids.backend.auth.oauth2.CustomAccessDeniedHandler;
import com.wekids.backend.auth.oauth2.CustomAuthenticationEntryPoint;
import com.wekids.backend.auth.oauth2.CustomSuccessHandler;
import com.wekids.backend.auth.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder passwordEncoder =
                (DelegatingPasswordEncoder)
                        PasswordEncoderFactories.createDelegatingPasswordEncoder();
        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return passwordEncoder;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of(
                    "http://localhost:3000",
                    "https://we-kids-fe-gold.vercel.app"
            ));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setMaxAge(3600L);
            configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
            configuration.addExposedHeader("Authorization");
            return configuration;
        };
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setSameSite("None"); // 'Strict', 'Lax', 'None' 중 선택
        serializer.setUseSecureCookie(true); // none 사용시 필수 설정
        return serializer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.csrf((auth)->auth.disable());

        http.formLogin((auth)-> auth.disable());

        http.httpBasic((auth)->auth.disable());

        http
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

        http
                .oauth2Login((oauth2)-> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler));

        http
                .exceptionHandling((exceptionConfig) -> exceptionConfig
                        .authenticationEntryPoint(authenticationEntryPoint)
                         .accessDeniedHandler(accessDeniedHandler)
                );

        http
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/health").permitAll()
                        .requestMatchers("/api/v1/signup").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http.sessionManagement((session) ->session
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));
        
        http.headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    if (response.containsHeader("Set-Cookie")) {
                        String originalHeader = response.getHeader("Set-Cookie");
                        response.setHeader("Set-Cookie", originalHeader + "; SameSite=None; Secure");
                    }
                })
        );

        return http.build();
    }
}
