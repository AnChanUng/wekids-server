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

    private String simplePassword;

    @Column(nullable = false)
    private String social;

    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MemberState state  = MemberState.ACTIVE;

    private LocalDateTime inactiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CardState cardState = CardState.NONE;

    private Long bankMemberId;

    public void saveBankMemberId(Long bankMemberId) {
        this.bankMemberId = bankMemberId;
    }
    public void saveSimplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
    }
    public void updateCardState(CardState cardState) {
        this.cardState = cardState;
    }
    public void updateState(MemberState state) {
        if(state.equals(MemberState.SLEEP) || state.equals(MemberState.LEAVE)){
            this.inactiveDate = LocalDateTime.now();
        }
        this.state = state;
    }

    public void updateSocialInfo(String name, String phone, String email, LocalDate birthday, String profile){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.profile = profile;
    }

}
