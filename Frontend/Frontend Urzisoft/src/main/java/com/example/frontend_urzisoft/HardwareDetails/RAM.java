package com.example.frontend_urzisoft.HardwareDetails;

public class RAM {
    private String manufacturer;
    private String Capacity;
    private String frequency;
    private  String memoryType;

    private int bank;



    public RAM(String manufacturer, String memoryType, String Capacity, String frequency, int bank)
    {
        this.manufacturer = manufacturer;
        this.Capacity = Capacity;
        this.frequency = frequency;
        this.memoryType = memoryType;
        this.bank = bank;

    }


    public String getManufacturer() {
        return manufacturer;
    }

    public String getCapacity() {
        return Capacity;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public int getBank() {
        return bank;
    }
}
