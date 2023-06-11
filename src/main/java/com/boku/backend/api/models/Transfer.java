package com.boku.backend.api.models;

import java.util.UUID;

public class Transfer {
    private final UUID id;
    private final User sender;
    private final User recipient;
    private final double amount;

    public Transfer(UUID id, User sender, User recipient, double amount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }
}
