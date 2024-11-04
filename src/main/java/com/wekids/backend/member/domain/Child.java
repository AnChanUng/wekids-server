package com.wekids.backend.member.domain;


import com.wekids.backend.member.domain.mapping.ParentChild;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("C")
@Getter
public class Child extends Member{
}
