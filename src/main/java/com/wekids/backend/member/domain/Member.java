package com.wekids.backend.member.domain;

import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "member_type")
public abstract class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private LocalDate birthday;

    private String profile;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberState state;

    private LocalDate inactiveDate;
}
