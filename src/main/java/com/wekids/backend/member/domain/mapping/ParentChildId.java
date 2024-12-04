package com.wekids.backend.member.domain.mapping;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ParentChildId implements Serializable {
    private Long parent;
    private Long child;

    public static ParentChildId of(Long parentId, Long childId) {
        return ParentChildId.builder()
                .parent(parentId)
                .child(childId)
                .build();
    }
}
