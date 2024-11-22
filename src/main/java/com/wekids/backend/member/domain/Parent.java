package com.wekids.backend.member.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PARENT")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Parent extends Member{
}