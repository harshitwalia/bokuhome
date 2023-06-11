package com.boku.backend.api.services;

import com.boku.backend.api.dtos.WithdrawalRequestDto;
import com.boku.backend.api.dtos.WithdrawalResponseDto;
import com.boku.backend.api.exceptions.WithdrawalException;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.UserRepository;
import com.boku.backend.external.Address;
import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.WithdrawalServiceStub;
import com.boku.backend.external.WithdrawalState;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class WithdrawalService {
    private final WithdrawalServiceStub withdrawalServiceStub;
    private final UserRepository userRepository;


    public WithdrawalService(UserRepository userRepository,WithdrawalServiceStub withdrawalServiceStub) {
        this.withdrawalServiceStub = withdrawalServiceStub;
        this.userRepository = userRepository;
    }

    public WithdrawalId requestWithdrawalInternal(UUID senderId, Address address,double amount) throws WithdrawalException {
        Optional<User> sender = userRepository.findById(senderId);
        if(sender.isEmpty()){
            throw new WithdrawalException("Sender not found");
        }
        if (sender.get().getBalance() < amount) {
            throw new WithdrawalException("Insufficient balance for transfer");
        }

        try {
            WithdrawalId withdrawalId = this.generateWithdrawalId();
            withdrawalServiceStub.requestWithdrawal(withdrawalId, address, amount);
            return withdrawalId;
        } catch (IllegalArgumentException e) {
            throw new WithdrawalException("Invalid withdrawal request", e);
        }
    }
    public WithdrawalResponseDto requestWithdrawalInternal(WithdrawalRequestDto requestDto) throws WithdrawalException {
        try {
            UUID senderId = requestDto.getSenderId();
            Address address = requestDto.getAddress();
            double amount = requestDto.getAmount();
            WithdrawalId withdrawalId = requestWithdrawalInternal(senderId,address,amount);
            WithdrawalState withdrawalState = getRequestState(withdrawalId);
            return new WithdrawalResponseDto(withdrawalId, withdrawalState, "");
        }
        catch (WithdrawalException e) {
            return new WithdrawalResponseDto(new WithdrawalId(UUID.randomUUID()), WithdrawalState.FAILED, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new WithdrawalException("Invalid withdrawal request", e);
        }
    }
    public WithdrawalResponseDto requestWithdrawal(UUID senderId, Address externalAddress, double amount) {

        // Create a withdrawal request with the external address and amount
        WithdrawalRequestDto withdrawalRequest = new WithdrawalRequestDto(senderId,externalAddress, amount);

        // Make the API call to the withdrawal service stub
        WithdrawalResponseDto withdrawalResponseDto = requestWithdrawalInternal(withdrawalRequest);

        while(withdrawalResponseDto.getStatus()==WithdrawalState.PROCESSING){
            withdrawalResponseDto.setStatus(getRequestState(withdrawalResponseDto.getWithdrawalId()));
        }
        // Update the sender's account balance accordingly
        if(withdrawalResponseDto.getStatus()==WithdrawalState.COMPLETED) {
            User sender = userRepository.findById(senderId).get();
            sender.decreaseBalance(amount);
            userRepository.save(sender);
        }
        return withdrawalResponseDto;
    }
    public WithdrawalResponseDto requestWithdrawal(WithdrawalRequestDto withdrawalRequestDto) {
       return requestWithdrawal(withdrawalRequestDto.getSenderId(),withdrawalRequestDto.getAddress(),withdrawalRequestDto.getAmount());
    }

    public WithdrawalState getRequestState(WithdrawalId withdrawalId) throws WithdrawalException {
        try {
            return withdrawalServiceStub.getRequestState(withdrawalId);
        } catch (IllegalArgumentException e) {
            throw new WithdrawalException("Withdrawal not found", e);
        }
    }

    private WithdrawalId generateWithdrawalId() {
        return new WithdrawalId(UUID.randomUUID());
    }
}
