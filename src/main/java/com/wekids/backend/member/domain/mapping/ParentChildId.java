package com.wekids.backend.member.domain.mapping;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ParentChildId  implements Serializable {
    private Long parent;
    private Long child;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentChildId that = (ParentChildId) o;
        return Objects.equals(parent, that.parent) && Objects.equals(child, that.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, child);
    }

}
