package com.boku.backend.api.services;

import com.boku.backend.api.dtos.TransferRequestDto;
import com.boku.backend.api.dtos.TransferResponseDto;
import com.boku.backend.api.exceptions.BadRequestException;
import com.boku.backend.api.exceptions.NotFoundException;
import com.boku.backend.api.models.Transfer;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.TransferRepository;
import com.boku.backend.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.Address;

import java.util.UUID;

@Service
public class TransferService {
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    public TransferService(UserRepository userRepository, TransferRepository transferRepository) {
        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    public Transfer makeTransfer(UUID senderId, UUID recipientId, double amount) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new NotFoundException("Recipient not found"));

        if (sender.getBalance() < amount) {
            throw new BadRequestException("Insufficient balance for transfer");
        }

        sender.decreaseBalance(amount);
        recipient.increaseBalance(amount);

        userRepository.save(sender);
        userRepository.save(recipient);

        Transfer transfer = new Transfer(UUID.randomUUID(), sender, recipient, amount);
        transferRepository.save(transfer);

        return transfer;
    }
    public TransferResponseDto makeTransfer(TransferRequestDto requestDto) {
        Transfer transfer = makeTransfer(requestDto.getSenderId(),requestDto.getRecipientId(), requestDto.getAmount());
        return new TransferResponseDto(transfer.getId(), true, "");
    }


}
