package com.wekids.backend.config;

import com.wekids.backend.auth.controller.AuthArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String CLIENT_LOCALHOST = "http://localhost:3000";
    private static final String CLIENT_SECURE_LOCALHOST = "https://localhost:3000";
    private static final String CLIENT_PRODUCTION = "https://we-kids-fe-gold.vercel.app";

    private static final String CORS_ALLOWED_METHODS =
            "GET,POST,HEAD,PUT,PATCH,DELETE,TRACE,OPTIONS";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(CORS_ALLOWED_METHODS.split(","))
                .allowedOrigins(CLIENT_LOCALHOST, CLIENT_SECURE_LOCALHOST, CLIENT_PRODUCTION)
                .exposedHeaders(HttpHeaders.SET_COOKIE, HttpHeaders.LOCATION)
                .allowCredentials(true);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }
}