package com.wekids.backend.auth.dto.response;

import java.time.LocalDate;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getPhone();
    LocalDate getBirthday();
    String getProfile();
}
