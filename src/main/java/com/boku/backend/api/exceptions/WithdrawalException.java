package com.boku.backend.api.exceptions;

public class WithdrawalException extends RuntimeException {
    public WithdrawalException(String message) {
        super(message);
    }

    public WithdrawalException(String message, Throwable cause) {
        super(message, cause);
    }
}
