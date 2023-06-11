package com.boku.backend.api.controllers;

import com.boku.backend.api.dtos.TransferRequestDto;
import com.boku.backend.api.dtos.TransferResponseDto;
import com.boku.backend.api.exceptions.BadRequestException;
import com.boku.backend.api.services.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.Mockito.*;

class TransferControllerTest {
    private TransferController transferController;

    @Mock
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferController = new TransferController(transferService);
    }

    @Test
    void testCreateTransfer() {
        TransferRequestDto requestDto = new TransferRequestDto(UUID.randomUUID(),UUID.randomUUID(),50.0);
        // Set requestDto properties

        TransferResponseDto responseDto = new TransferResponseDto(UUID.randomUUID(),true,"");
        // Set responseDto properties

        when(transferService.makeTransfer(requestDto)).thenReturn(responseDto);

        ResponseEntity<TransferResponseDto> response = transferController.makeTransfer(requestDto);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(responseDto, response.getBody());

        verify(transferService, times(1)).makeTransfer(any(TransferRequestDto.class));
    }

    @Test
    void testCreateTransferInvalidRequest() {
        TransferRequestDto requestDto = new TransferRequestDto(UUID.randomUUID(),UUID.randomUUID(),50.0);
        // Set requestDto properties

        when(transferService.makeTransfer(requestDto)).thenThrow(BadRequestException.class);

        Assertions.assertThrows(BadRequestException.class, () ->transferController.makeTransfer(requestDto));
        verify(transferService, times(1)).makeTransfer(any(TransferRequestDto.class));
    }
}
