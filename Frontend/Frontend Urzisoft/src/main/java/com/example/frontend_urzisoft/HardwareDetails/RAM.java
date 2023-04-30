package com.example.frontend_urzisoft.HardwareDetails;

public class RAM {
    private String manufacturer;
    private String bankLabel;
    private String getCapacity;
    private String frequency;
    private  String memoryType;

    public RAM(String manufacturer,String memoryType ,String bankLabel, String getCapacity, String frequency) {
        this.manufacturer = manufacturer;
        this.bankLabel = bankLabel;
        this.getCapacity = getCapacity;
        this.frequency = frequency;
        this.memoryType = memoryType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBankLabel() {
        return bankLabel;
    }

    public void setBankLabel(String bankLabel) {
        this.bankLabel = bankLabel;
    }

    public String getGetCapacity() {
        return getCapacity;
    }

    public void setGetCapacity(String getCapacity) {
        this.getCapacity = getCapacity;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
}
