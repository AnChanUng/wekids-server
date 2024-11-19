package com.wekids.backend.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ParentAccountResponse {
    ParentResponse parent;
    List<ChildResponse> children;

    public static ParentAccountResponse of(ParentResponse parent, List<ChildResponse> children){
        return new ParentAccountResponse(parent, children);
    }

}
