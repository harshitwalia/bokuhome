package com.boku.backend.repositories;

import com.boku.backend.api.models.User;
import com.boku.backend.api.repositories.TransferRepository;
import com.boku.backend.api.models.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class TransferRepositoryTest {
    private TransferRepository transferRepository;

    @BeforeEach
    public void setup() {
        // Initialize TransferRepository or mock it using a mocking framework
        transferRepository = new TransferRepository();

    }

    @Test
    public void testSaveTransfer() {
        User user1 = new User(UUID.randomUUID(),"John Doe", 100.0);
        User user2 = new User(UUID.randomUUID(),"John Doe", 100.0);

        // Create a new transfer
        Transfer transfer = new Transfer(UUID.randomUUID(), user1, user2, 50.0);

        // Save the transfer
        transferRepository.save(transfer);

        // Retrieve the saved transfer
        Transfer savedTransfer = transferRepository.findById(transfer.getId()).get();

        // Check if the saved transfer is not null and has the same properties
        Assertions.assertNotNull(savedTransfer);
        Assertions.assertEquals(transfer.getId(), savedTransfer.getId());
        Assertions.assertEquals(transfer.getSender().getId(), savedTransfer.getSender().getId());
        Assertions.assertEquals(transfer.getRecipient().getId(), savedTransfer.getRecipient().getId());
        Assertions.assertEquals(transfer.getAmount(), savedTransfer.getAmount());
    }

    @Test
    public void testGetTransferById() {
        User user1 = new User(UUID.randomUUID(),"John Doe", 100.0);
        User user2 = new User(UUID.randomUUID(),"John Doe", 100.0);

        // Create a new transfer
        Transfer transfer = new Transfer(UUID.randomUUID(), user1, user2, 50.0);

        // Save the transfer
        transferRepository.save(transfer);

        // Retrieve the transfer by ID
        Transfer retrievedTransfer = transferRepository.findById(transfer.getId()).get();

        // Check if the retrieved transfer is not null and has the same properties
        Assertions.assertNotNull(retrievedTransfer);
        Assertions.assertEquals(transfer.getId(), retrievedTransfer.getId());
        Assertions.assertEquals(transfer.getSender().getId(), retrievedTransfer.getSender().getId());
        Assertions.assertEquals(transfer.getRecipient().getId(), retrievedTransfer.getRecipient().getId());
        Assertions.assertEquals(transfer.getAmount(), retrievedTransfer.getAmount());
    }

    // Add more test cases for other TransferRepository methods

}
