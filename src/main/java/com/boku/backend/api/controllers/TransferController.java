package com.boku.backend.api.controllers;

import com.boku.backend.api.dtos.TransferRequestDto;
import com.boku.backend.api.dtos.TransferResponseDto;
import com.boku.backend.api.models.Transfer;
import com.boku.backend.api.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/")
    public ResponseEntity<TransferResponseDto> makeTransfer(@RequestBody TransferRequestDto transferRequestDto) {
        TransferResponseDto responseDto = transferService.makeTransfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
