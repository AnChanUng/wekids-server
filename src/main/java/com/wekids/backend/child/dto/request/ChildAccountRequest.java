package com.wekids.backend.child.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChildAccountRequest {
    Long childId;

    public ChildAccountRequest(Long childId) {
        this.childId = childId;
    }
}
