package com.wekids.backend.child.service;

import com.wekids.backend.child.dto.response.ChildResponseDto;
import com.wekids.backend.child.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChildServiceImpl implements ChildService {
    @Autowired
    private ChildRepository childRepository;


    @Override
    public List<ChildResponseDto> getAllChildren() {
        return childRepository.findAll().stream()
                .map(ChildResponseDto::new) // Child를 DTO로 변환
                .collect(Collectors.toList());
    }
}
