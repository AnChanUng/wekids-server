package com.wekids.backend.support.fixture;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import lombok.Builder;

@Builder
public class ParentChildFixture {
    private Long parentId;
    private Long childId;
    @Builder.Default
    private Parent parent = ParentFixture.builder().id(1L).build().parent();
    @Builder.Default
    private Child child = ChildFixture.builder().id(1L).build().child();

    public ParentChild parentChild() {
        return ParentChild.builder()
                .parent(parent)
                .child(child)
                .build();
    }
}
