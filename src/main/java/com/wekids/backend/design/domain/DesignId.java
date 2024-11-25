package com.wekids.backend.design.domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DesignId implements Serializable {
    private Long member;
}
