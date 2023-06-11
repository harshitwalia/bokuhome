package com.boku.backend.external;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WithdrawalServiceStub implements WithdrawalService {
    private final Map<WithdrawalId, Withdrawal> requests = new ConcurrentHashMap<>();

    @Override
    public void requestWithdrawal(WithdrawalId id, Address address, double amount) {
        final var existing = requests.putIfAbsent(id, new Withdrawal(finalState(), finaliseAt(), address, amount));
        if (existing != null && (!Objects.equals(existing.address, address) || existing.amount != amount) ){
            throw new IllegalArgumentException("Withdrawal request with id[" + id + "] is already present");
        }
    }

    @Override
    public WithdrawalState getRequestState(WithdrawalId id) {
        final var request = requests.get(id);
        if (request == null) {
            throw new IllegalArgumentException("Request " + id + " is not found");
        }
        return request.finalState();
    }

    private WithdrawalState finalState() {
        return ThreadLocalRandom.current().nextBoolean() ? WithdrawalState.COMPLETED : WithdrawalState.FAILED;
    }

    private long finaliseAt() {
        return System.currentTimeMillis() + ThreadLocalRandom.current().nextLong(1000, 10000);
    }

    private static record Withdrawal(WithdrawalState state, long finaliseAt, Address address, double amount) {
        public WithdrawalState finalState() {
            return finaliseAt <= System.currentTimeMillis() ? state : WithdrawalState.PROCESSING;
        }
    }
}
