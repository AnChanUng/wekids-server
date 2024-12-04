package com.wekids.backend.design.service;

import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;

public interface DesignService {
    DesignResponse showDesign(Long designId);
    void createDesign(Long childId, DesignCreateRequest request);
}
