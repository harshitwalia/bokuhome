package com.boku.backend.api.dtos;

import com.boku.backend.external.WithdrawalState;

public class WithdrawalStateDto {
    private WithdrawalState state;

    public WithdrawalStateDto(WithdrawalState state) {
        this.state = state;
    }

    public WithdrawalState getState() {
        return state;
    }

    public void setState(WithdrawalState state) {
        this.state = state;
    }
}

