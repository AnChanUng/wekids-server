package com.wekids.backend.design.domain;

import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DesignId implements Serializable {

    private Long member;
}
