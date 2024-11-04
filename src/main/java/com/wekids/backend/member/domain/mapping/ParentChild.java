package com.wekids.backend.member.domain.mapping;

import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import jakarta.persistence.*;

@Entity
@IdClass(ParentChildId.class)
public class ParentChild extends BaseTime {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;
}
