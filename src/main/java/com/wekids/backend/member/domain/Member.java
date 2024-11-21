package com.wekids.backend.member.domain;

import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "member_type")
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(length = 20)
    private String simplePassword;

    @Enumerated(EnumType.STRING) // 이거 default값 왜 안먹지??
    @Column(nullable = false)
    @Builder.Default
    private MemberState state = MemberState.ACTIVE;

    private LocalDateTime inactiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CardState cardState = CardState.CREATED;

    private Long bankMemberId;

}
