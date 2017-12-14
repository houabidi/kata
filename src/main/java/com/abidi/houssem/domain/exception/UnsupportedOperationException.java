package com.abidi.houssem.domain.exception;

import lombok.Getter;

/**
 * Created by houssemabidi on 13/12/17.
 */
@Getter
public class UnsupportedOperationException extends RuntimeException {

    private final String message;

    public UnsupportedOperationException(String message) {
        this.message = message;
    }


}
