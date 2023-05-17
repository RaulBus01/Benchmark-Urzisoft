package com.example.frontend_urzisoft.DataBase;

public class LeaderboardEntry {

    private String user;
    private String CPU;

    private String RAM;
    private int CPUScore;
    private int RAMScore;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public int getCPUScore() {
        return CPUScore;
    }

    public void setCPUScore(int CPUScore) {
        this.CPUScore = CPUScore;
    }

    public int getRAMScore() {
        return RAMScore;
    }

    public void setRAMScore(int RAMScore) {
        this.RAMScore = RAMScore;
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    private int TotalScore;

    public LeaderboardEntry(String user,String CPU, String RAM, int CPUScore,int RAMScore,int TotalScore)
    {
        this.user = user;
        this.CPU = CPU;
        this.RAM = RAM;
        this.CPUScore = CPUScore;
        this.RAMScore = RAMScore;
        this.TotalScore = TotalScore;
    }
}
