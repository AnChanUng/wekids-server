package com.wekids.backend.baas.service;

import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.baas.aop.BassLogAndHandleException;
import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BassServiceImpl implements BaasService {
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    @Value("${baas.api.baas-url}")
    private String baasURL;
    @Value("${baas.id.baas-member-id}")
    private Long baasMemberId;

    @Override
    @BassLogAndHandleException
    public List<AllAccountResponse> showAccounts(Long memberId) {
        Member member = findMemberByMemberId(memberId);
        String url = baasURL + "/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";
        ResponseEntity<List<BaasAccountResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BaasAccountResponse>>() {},
                baasMemberId,
                member.getBankMemberId()
        );
        return response.getBody().stream()
                .map(AllAccountResponse::new)
                .collect(Collectors.toList());
    }

    private Member findMemberByMemberId(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                ()-> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, memberId + "가 없습니다."));
    }
}