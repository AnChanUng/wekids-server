package com.wekids.backend.admin.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.admin.dto.response.MemberInfoResponse;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.member.repository.ParentChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final DesignRepository designRepository;
    private final ParentChildRepository parentChildRepository;

    @Override
    public MemberInfoResponse showMember(String name, String phone) {
        Member member = findMemberByNameAndPhone(name, phone);
        Account account = findAccountByMember(member);
        Card card = findCardByAccount(account);
        Design design = findDesignByMember(member);
        List<Member> family = (member.getRole().endsWith("PARENT")) ?
                parentChildRepository.findChildrenByParentId(member.getId()) :
                parentChildRepository.findParentsByChildId(member.getId());
        return MemberInfoResponse.of(member, card, account, design, family);
    }

    private Member findMemberByNameAndPhone(String name, String phone){
        return memberRepository.findByNameAndPhone(name, phone)
                .orElseThrow(()-> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, name + ", " + phone + "의 사용자는 없습니다"));
    }

    private Account findAccountByMember(Member member){
        return accountRepository.findByMember(member).orElse(null);
    }

    private Card findCardByAccount(Account account){
        if(account == null) return null;

        return cardRepository.findByAccount(account).orElse(null);
    }

    private Design findDesignByMember(Member member){
        return designRepository.findByMember(member).orElse(null);
    }
}
