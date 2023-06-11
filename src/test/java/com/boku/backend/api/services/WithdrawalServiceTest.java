package com.boku.backend.api.services;

import com.boku.backend.api.dtos.WithdrawalResponseDto;
import com.boku.backend.api.exceptions.WithdrawalException;
import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.UserRepository;
import com.boku.backend.external.Address;
import com.boku.backend.external.WithdrawalServiceStub;
import com.boku.backend.external.WithdrawalId;
import com.boku.backend.external.WithdrawalState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class WithdrawalServiceTest {
    private WithdrawalService withdrawalService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        withdrawalService = new WithdrawalService(userRepository,new WithdrawalServiceStub());
    }

    @Test
    void testRequestWithdrawal() {
        User user = new User(UUID.randomUUID(),"John Doe1", 100.0);
        Address externalAddress = new Address("withdrawal_address");
        // Mock the repository behavior
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        // Test withdrawal request
        WithdrawalResponseDto responseDto = withdrawalService.requestWithdrawal(user.getId(), externalAddress, 100.0);

        // Test withdrawal state
        WithdrawalState withdrawalState = withdrawalService.getRequestState(responseDto.getWithdrawalId());

        Assertions.assertNotNull(withdrawalState);
        Assertions.assertTrue(withdrawalState == WithdrawalState.COMPLETED || withdrawalState == WithdrawalState.FAILED);
    }

    @Test
    void testWithdrawalRequestNotFound() {
        WithdrawalId withdrawalId = new WithdrawalId(UUID.randomUUID());

        // Test withdrawal state for a non-existing withdrawal request
        Assertions.assertThrows(WithdrawalException.class, () -> withdrawalService.getRequestState(withdrawalId));
    }


}
