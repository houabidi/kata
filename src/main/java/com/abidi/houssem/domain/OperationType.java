package com.abidi.houssem.domain;

/**
 * Created by houssemabidi on 12/12/17.
 */
public enum OperationType {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
