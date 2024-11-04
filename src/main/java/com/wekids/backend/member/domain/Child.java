package com.wekids.backend.member.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("C")
@Getter
public class Child extends Member{
}
