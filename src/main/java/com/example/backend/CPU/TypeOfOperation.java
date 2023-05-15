package com.example.backend.CPU;

public enum TypeOfOperation {
    ARITHMETIC(29),// 39%
    BRANCHING(11),// 15%
    ASSIGNMENT(34);// 46%

    private final long value;

    private TypeOfOperation(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

}
