package com.example.backend.CPU.timing;

public interface ITimer {
    void start();
    long stop();
    void resume();
    long pause();
    long transformToUnitTime(UnitTime tUnit, long t);
}
