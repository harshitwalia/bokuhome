package com.boku.backend.api.dtos;

import java.util.UUID;

public class TransferResponseDto {
    private UUID transferId;
    private boolean success;
    private String message;

    public TransferResponseDto(UUID transferId, boolean success, String message) {
        this.transferId = transferId;
        this.success = success;
        this.message = message;
    }

    public UUID getTransferId() {
        return transferId;
    }

    public void setTransferId(UUID transferId) {
        this.transferId = transferId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
