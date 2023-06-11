package com.boku.backend.api.services;

import com.boku.backend.api.dtos.TransferRequestDto;
import com.boku.backend.api.dtos.TransferResponseDto;
import com.boku.backend.api.exceptions.BadRequestException;
import com.boku.backend.api.exceptions.TransferException;
import com.boku.backend.api.models.Transfer;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.TransferRepository;
import com.boku.backend.api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class TransferServiceTest {
    private TransferService transferService;
    private User user1 = new User(UUID.randomUUID(),"John Doe1", 100.0);
    private User user2 = new User(UUID.randomUUID(),"John Doe2", 100.0);
    @Mock
    private TransferRepository transferRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(userRepository, transferRepository);
    }

    @Test
    public void testMakeTransfer_Success() throws TransferException {
        userRepository.save(user1);
        userRepository.save(user2);
        // Prepare test data
        TransferRequestDto requestDto = new TransferRequestDto(user1.getId(), user2.getId(), 100.0);
        Transfer transfer = new Transfer(UUID.randomUUID(), user1, user2, 100.0);

        // Mock the repository behavior
        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.ofNullable(user2));
        when(transferRepository.saveTransfer(transfer)).thenReturn(transfer);

        // Perform the transfer
        TransferResponseDto responseDto = transferService.makeTransfer(requestDto);
        TransferResponseDto expectedResponseDto = new TransferResponseDto(responseDto.getTransferId(), true, "");

        // Verify the response
        Assertions.assertEquals(expectedResponseDto.getTransferId(), responseDto.getTransferId());
        Assertions.assertEquals(expectedResponseDto.getMessage(), responseDto.getMessage());
        Assertions.assertEquals(expectedResponseDto.isSuccess(), responseDto.isSuccess());
    }

    @Test
    public void testMakeTransfer_InsufficientBalance() {
        userRepository.save(user1);
        userRepository.save(user2);
        // Prepare test data
        TransferRequestDto requestDto = new TransferRequestDto(user1.getId(), user2.getId(), 200.0);

        // Mock the repository behavior to simulate insufficient balance
        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.ofNullable(user2));

        // Perform the transfer and verify the exception
        Assertions.assertThrows(BadRequestException.class, () -> transferService.makeTransfer(requestDto));
    }

    // Add more test cases for other TransferService methods

}
