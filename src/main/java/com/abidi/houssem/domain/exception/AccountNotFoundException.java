package com.abidi.houssem.domain.exception;

import lombok.Getter;

/**
 * Created by houssemabidi on 13/12/17.
 */
@Getter
public class AccountNotFoundException extends RuntimeException {

    private final String message;

    public AccountNotFoundException(String message) {
        this.message = message;
    }
}
