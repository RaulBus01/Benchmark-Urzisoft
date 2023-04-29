package com.example.frontend_urzisoft.HardwareDetails;

public class CPU {
    private String name;
    private long score;

    private int physicalCores;
    private int logicalCores;

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getPhysicalCores() {
        return physicalCores;
    }

    public void setPhysicalCores(int physicalCores) {
        this.physicalCores = physicalCores;
    }

    public int getLogicalCores() {
        return logicalCores;
    }

    public void setLogicalCores(int logicalCores) {
        this.logicalCores = logicalCores;
    }

    public CPU(String name, int physicalCores, int logicalCores)
    {
        this.name = name;

        this.physicalCores = physicalCores;
        this.logicalCores = logicalCores;
    }


}
