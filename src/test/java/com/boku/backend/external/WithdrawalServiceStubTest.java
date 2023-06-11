package com.boku.backend.external;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class WithdrawalServiceStubTest {

    @Test
    void testWithdrawalRequestAndState() {
        WithdrawalServiceStub withdrawalService = new WithdrawalServiceStub();

        WithdrawalId withdrawalId = new WithdrawalId(UUID.randomUUID());
        Address address = new Address("withdrawal_address");
        double amount = 100.0;

        // Test withdrawal request
        withdrawalService.requestWithdrawal(withdrawalId, address, amount);

        // Test withdrawal state
        WithdrawalState withdrawalState = withdrawalService.getRequestState(withdrawalId);

        Assertions.assertNotNull(withdrawalState);
        Assertions.assertTrue(withdrawalState == WithdrawalState.PROCESSING ||withdrawalState == WithdrawalState.COMPLETED || withdrawalState == WithdrawalState.FAILED);
    }

    @Test
    void testWithdrawalRequestNotFound() {
        WithdrawalServiceStub withdrawalService = new WithdrawalServiceStub();

        WithdrawalId withdrawalId = new WithdrawalId(UUID.randomUUID());

        // Test withdrawal state for a non-existing withdrawal request
        Assertions.assertThrows(IllegalArgumentException.class, () -> withdrawalService.getRequestState(withdrawalId));
    }

    @Test
    void testWithdrawalRequestWithDifferentAddressOrAmount() {
        WithdrawalServiceStub withdrawalService = new WithdrawalServiceStub();

        WithdrawalId withdrawalId = new WithdrawalId(UUID.randomUUID());
        Address address1 = new Address("address_1");
        Address address2 = new Address("address_2");
        double amount = 100.0;

        // Test withdrawal request with different address
        withdrawalService.requestWithdrawal(withdrawalId, address1, amount);
        Assertions.assertThrows(IllegalArgumentException.class, () -> withdrawalService.requestWithdrawal(withdrawalId, address2, amount));

        // Test withdrawal request with different amount
        withdrawalService.requestWithdrawal(withdrawalId, address1, amount);
        Assertions.assertThrows(IllegalArgumentException.class, () -> withdrawalService.requestWithdrawal(withdrawalId, address1, amount + 50.0));
    }
}
