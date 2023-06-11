package com.boku.backend.api.dtos;

import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.WithdrawalState;

public class WithdrawalResponseDto {
    private WithdrawalId withdrawalId;
    private WithdrawalState status;
    private String message;


    public WithdrawalResponseDto(WithdrawalId withdrawalId, WithdrawalState status, String message) {
        this.withdrawalId = withdrawalId;
        this.status = status;
        this.message = message;
    }

    public WithdrawalId getWithdrawalId() {
        return withdrawalId;
    }

    public void setWithdrawalId(WithdrawalId withdrawalId) {
        this.withdrawalId = withdrawalId;
    }

    public WithdrawalState getStatus() {
        return status;
    }

    public void setStatus(WithdrawalState status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
