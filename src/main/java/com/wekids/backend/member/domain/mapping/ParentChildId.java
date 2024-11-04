package com.wekids.backend.member.domain.mapping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import lombok.EqualsAndHashCode;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParentChildId implements Serializable {
    private Long parent;
    private Long child;
}
