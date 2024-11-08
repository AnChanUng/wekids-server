package com.wekids.backend.child.controller;

import com.wekids.backend.child.dto.response.ChildResponseDto;
import com.wekids.backend.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping
    public List<ChildResponseDto> getAllChildren() {
        return childService.getAllChildren();
    }

}
