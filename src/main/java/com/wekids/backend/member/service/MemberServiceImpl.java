package com.wekids.backend.member.service;

import com.wekids.backend.admin.dto.request.MemberStateRequest;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.dto.response.MyPageResponse;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public void updateMemberState(Long memberId, MemberStateRequest request) {
        Member member = findMemberByMemberId(memberId);
        member.updateState(request.getState());
    }

    @Override
    public MyPageResponse showMyPage(Long memberId) {
        Member member = findMemberByMemberId(memberId);
        return MyPageResponse.from(member);
    }

    public Member findMemberByMemberId(Long memberId){
        return memberRepository.findById(memberId).orElseThrow( ()->
                new WekidsException(ErrorCode.MEMBER_NOT_FOUND, memberId + "의 회원은 없습니다")
        );
    }
}
