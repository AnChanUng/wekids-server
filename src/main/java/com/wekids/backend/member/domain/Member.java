package com.wekids.backend.member.domain;

import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Column(nullable = false)
    private String social;

    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberState state;

    private LocalDateTime inactiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'NONE'")
    private CardState cardState;

    private Long bankMemberId;

    public void updateSocialInfo(String name, String phone, String email, LocalDate birthday, String profile){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.profile = profile;
    }

}
