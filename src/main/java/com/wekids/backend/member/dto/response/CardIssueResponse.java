package com.wekids.backend.member.dto.response;

import com.wekids.backend.card.domain.Card;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardIssueResponse {
    private String cardNumber;
    private LocalDate validThru;
    private String cvc;
    private String mebmerName;
    private LocalDateTime newDate;

    public CardIssueResponse(Card card) {
        this.cardNumber = card.getCardNumber();
        this.validThru = card.getValidThru();
        this.cvc = card.getCvc();
        this.mebmerName = card.getMemberName();
        this.newDate = card.getNewDate();
    }
}
