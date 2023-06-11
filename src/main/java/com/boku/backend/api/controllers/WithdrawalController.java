package com.boku.backend.api.controllers;

import com.boku.backend.api.dtos.WithdrawalRequestDto;
import com.boku.backend.api.dtos.WithdrawalResponseDto;
import com.boku.backend.api.dtos.WithdrawalStateDto;
import com.boku.backend.api.exceptions.WithdrawalException;
import com.boku.backend.api.services.WithdrawalService;
import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.WithdrawalState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/withdrawals")
public class WithdrawalController {
    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public ResponseEntity<WithdrawalResponseDto> requestWithdrawal(@RequestBody WithdrawalRequestDto requestDto) {
        WithdrawalResponseDto responseDto = withdrawalService.requestWithdrawal(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WithdrawalStateDto> getWithdrawalState(@PathVariable("id") UUID withdrawalId) {
        WithdrawalState state = withdrawalService.getRequestState(new WithdrawalId(withdrawalId));
        WithdrawalStateDto stateDto = new WithdrawalStateDto(state);
        return ResponseEntity.ok(stateDto);
    }
}

