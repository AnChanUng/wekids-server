package com.wekids.backend.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParentAccountRequest {
    Long parentId;

    public ParentAccountRequest(Long parentId){
        this.parentId = parentId;
    }
}
