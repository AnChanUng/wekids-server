package com.wekids.backend.baas.service;

import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.support.fixture.ParentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BassServiceTest {
    @Mock private MemberRepository memberRepository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks private BassServiceImpl bassService;
    @Value("${baas.api.baas-url}")
    private String baasURL;
    @Value("${baas.id.baas-member-id}")
    private Long baasMemberId;

    @Test
    void 전_계좌_리스트_조회를_할_수_있다() {
        // GIVEN
        Member member = ParentFixture.builder().id(1L).bankMemberId(1L).build().parent();

        List<BaasAccountResponse> baasAccountResponses = List.of(
                new BaasAccountResponse("123-456-789", "우리은행", new BigDecimal(1000.0), "ACTIVE", "안찬웅", "상품명", "입출금통장"),
                new BaasAccountResponse("987-654-321", "하나은행", new BigDecimal(1000.0), "ACTIVE", "조예은", "상품명", "입출금통장")
        );

        List<AllAccountResponse> expectedAccounts = baasAccountResponses.stream()
                .map(AllAccountResponse::new)
                .toList();

        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(restTemplate.exchange(
                eq(baasURL + "/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts"),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class),
                eq(baasMemberId),
                eq(member.getBankMemberId())
        )).willReturn(ResponseEntity.ok(baasAccountResponses));

        // When
        List<AllAccountResponse> result = bassService.showAccounts(member.getId());

        // Then
        for (int i = 0; i < result.size(); i++) {
            AllAccountResponse actual = result.get(i);
            AllAccountResponse expected = expectedAccounts.get(i);

            assertThat(actual.getAccountNumber()).isEqualTo(expected.getAccountNumber());
            assertThat(actual.getBankName()).isEqualTo(expected.getBankName());
            assertThat(actual.getBalance()).isEqualTo(expected.getBalance());
        }

        verify(restTemplate).exchange(
                eq(baasURL + "/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts"),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class),
                eq(baasMemberId),
                eq(member.getBankMemberId())
        );
    }

    @Test
    void 멤버가_존재하지_않을_때_예외가_발생한다() {
        // GIVEN
        Long memberId = 1L;

        // Mock: 멤버가 존재하지 않음
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // WHEN & THEN
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                WekidsException.class, () -> bassService.showAccounts(memberId)
        );

        assertThat(exception.getMessage()).contains(memberId + "가 없습니다.");
        verify(memberRepository).findById(memberId);
    }
}
