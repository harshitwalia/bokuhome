package com.boku.backend.external;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

public interface WithdrawalService {
    /**
     * Request a withdrawal for a given address and amount. Completes at random moment between 1 and 10 seconds.
     *
     * @param id      - a caller generated withdrawal id, used for idempotency
     * @param address - an address to withdraw to, can be any arbitrary string
     * @param amount  - an amount to withdraw
     * @throws IllegalArgumentException in case there's a different address or amount for the given id
     */
    void requestWithdrawal(WithdrawalId id, Address address, double amount);

    /**
     * Return the current state of the withdrawal.
     *
     * @param id - a withdrawal id
     * @return current state of the withdrawal
     * @throws IllegalArgumentException in case there's no withdrawal for the given id
     */
    WithdrawalState getRequestState(WithdrawalId id);




}
