package com.wekids.backend.card.service;

import com.wekids.backend.card.dto.request.IssueRequest;

public interface CardIssueService {
    void issueAccountAndCard(IssueRequest issueRequest, Long parentId);

}
