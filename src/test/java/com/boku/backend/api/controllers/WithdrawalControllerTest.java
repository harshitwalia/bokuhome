package com.boku.backend.api.controllers;

import com.boku.backend.api.dtos.WithdrawalRequestDto;
import com.boku.backend.api.dtos.WithdrawalResponseDto;
import com.boku.backend.api.exceptions.BadRequestException;
import com.boku.backend.api.exceptions.WithdrawalException;
import com.boku.backend.api.services.WithdrawalService;
import com.boku.backend.external.Address;
import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.WithdrawalState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.Mockito.*;

class WithdrawalControllerTest {

    private WithdrawalController withdrawalController;

    @Mock
    private WithdrawalService withdrawalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        withdrawalController = new WithdrawalController(withdrawalService);
    }

    @Test
    void testRequestWithdrawal() {
        WithdrawalRequestDto requestDto = new WithdrawalRequestDto(UUID.randomUUID(), new Address("externalAddress"), 100.0);
        // Set requestDto properties

        WithdrawalResponseDto responseDto = new WithdrawalResponseDto(new WithdrawalId(UUID.randomUUID()), WithdrawalState.PROCESSING,"");
        // Set responseDto properties

        when(withdrawalService.requestWithdrawal(requestDto)).thenReturn(responseDto);

        ResponseEntity<WithdrawalResponseDto> response = withdrawalController.requestWithdrawal(requestDto);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(responseDto, response.getBody());

        verify(withdrawalService, times(1)).requestWithdrawal(any(WithdrawalRequestDto.class));
    }

    @Test
    void testRequestWithdrawalInvalidRequest() {
        WithdrawalRequestDto requestDto = new WithdrawalRequestDto(UUID.randomUUID(), new Address("externalAddress"), 100.0);
        // Set requestDto properties

        when(withdrawalService.requestWithdrawal(requestDto)).thenThrow(WithdrawalException.class);

        Assertions.assertThrows(WithdrawalException.class, () -> withdrawalController.requestWithdrawal(requestDto));
        verify(withdrawalService, times(1)).requestWithdrawal(any(WithdrawalRequestDto.class));
    }
}
