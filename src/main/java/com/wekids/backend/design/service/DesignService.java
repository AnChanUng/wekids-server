package com.wekids.backend.design.service;

import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;

public interface DesignService {
    DesignResponse showDesign(Long memberId);
    DesignResponse createDesign(Long memberId, DesignCreateRequest request);
}
