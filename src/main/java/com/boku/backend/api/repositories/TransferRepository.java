package com.boku.backend.api.repositories;

import com.boku.backend.api.models.Transfer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class TransferRepository {
    private final List<Transfer> transfers;

    public TransferRepository() {
        this.transfers = new ArrayList<>();
    }

    public void save(Transfer transfer) {
        transfers.add(transfer);
    }
    public Transfer saveTransfer(Transfer transfer) {
        save(transfer); return transfer;
    }

    public Optional<Transfer> findById(UUID transferId) {
        return transfers.stream()
                .filter(transfer -> transfer.getId().equals(transferId))
                .findFirst();
    }

    public List<Transfer> findAll() {
        return new ArrayList<>(transfers);
    }
}
