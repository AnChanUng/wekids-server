package com.wekids.backend.child.service;

import com.wekids.backend.child.dto.response.ChildResponseDto;
import com.wekids.backend.child.repository.ChildRepository;
import com.wekids.backend.member.domain.Child;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ChildService {

    List<ChildResponseDto> getAllChildren();
}
