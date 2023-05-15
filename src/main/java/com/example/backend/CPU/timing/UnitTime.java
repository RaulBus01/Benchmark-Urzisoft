package com.example.backend.CPU.timing;

public enum UnitTime {
    NANOSECONDS(1),
    MICROSECONDS(1000),
    MILLISECONDS(1000000),
    SECONDS(1000000000);

    private final long value;

    private UnitTime(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public String getUnit() {
        return this.name();
    }
}
