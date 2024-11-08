package com.wekids.backend.child.service;

import com.wekids.backend.child.dto.response.ChildResponseDto;

import java.util.List;

public interface ChildService {

    List<ChildResponseDto> getAllChildren();
}
