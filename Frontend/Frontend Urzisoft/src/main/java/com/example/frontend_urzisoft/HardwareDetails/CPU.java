package com.example.frontend_urzisoft.HardwareDetails;

public class CPU {
    private String name;
    private long score;

    private int physicalCores;
    private int logicalCores;
    private String frequency;



    public CPU(String name, int physicalCores, int logicalCores, String frequency)
    {
        this.name = name;
        this.frequency = frequency;
        this.physicalCores = physicalCores;
        this.logicalCores = logicalCores;
    }


}
