package com.wekids.backend.auth.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class NaverResponse implements OAuth2Response{
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        System.out.println(attribute.get("response").toString());
        this.attribute = (Map<String, Object>) attribute.get("response") ;
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getPhone() {
        return attribute.get("mobile").toString();
    }

    @Override
    public String getProfile() {
        return attribute.get("profile_image").toString();
    }


    @Override
    public LocalDate getBirthday() {
        String fullDate = attribute.get("birthyear") + "-" + attribute.get("birthday");
        return LocalDate.parse(fullDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
