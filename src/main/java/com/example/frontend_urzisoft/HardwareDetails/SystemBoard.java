package com.example.frontend_urzisoft.HardwareDetails;

public class SystemBoard {

    private String motherboard;
    private String operatingSystem;

    private String model;

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public SystemBoard(String motherboard, String operatingSystem, String model) {
        this.motherboard = motherboard;
        this.operatingSystem = operatingSystem;
        this.model = model;
    }


}
