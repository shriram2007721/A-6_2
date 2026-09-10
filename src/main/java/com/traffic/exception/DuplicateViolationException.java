package com.traffic.exception;

public class DuplicateViolationException extends RuntimeException {

    public DuplicateViolationException(String message) {
        super(message);
    }
}
