package com.wekids.backend.member.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CHILD")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Child extends Member {

}
