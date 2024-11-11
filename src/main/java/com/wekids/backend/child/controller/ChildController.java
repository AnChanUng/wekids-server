package com.wekids.backend.child.controller;

import com.wekids.backend.child.dto.response.ChildAccountResponse;
import com.wekids.backend.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @GetMapping("/api/v1/children")
    public ResponseEntity<ChildAccountResponse> getParentAccount(){
        ChildAccountResponse response = childService.getChildAccount();
        return ResponseEntity.ok(response);
    }

}
