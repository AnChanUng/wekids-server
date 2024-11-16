package com.wekids.backend.accountTransaction.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateMemoRequest {

    @Size(max = 20, message = "메모 내용은 최대 20자까지 입력 가능합니다.")
    private String memo;

    private UpdateMemoRequest(){}

    public UpdateMemoRequest(String memo){this.memo = memo;}
}
