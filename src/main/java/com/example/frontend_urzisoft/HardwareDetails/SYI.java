package com.example.frontend_urzisoft.HardwareDetails;

public class SYI {
    private String OperatingSystem;
    private String Model;
    private String Motherboard;

    public SYI(String OperatingSystem, String Model, String Motherboard)
    {
        this.OperatingSystem = OperatingSystem;
        this.Model = Model;
        this.Motherboard = Motherboard;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public String getModel() {
        return Model;
    }

    public String getMotherboard() {
        return Motherboard;
    }
}
