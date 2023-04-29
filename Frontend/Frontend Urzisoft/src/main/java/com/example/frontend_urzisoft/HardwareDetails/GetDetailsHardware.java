package com.example.frontend_urzisoft.HardwareDetails;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GraphicsCard;
import oshi.util.FormatUtil;

public class GetDetailsHardware
{
public void getHardwareInfo() {
        // Retrieve the operating system MXBean
    SystemInfo sys = new SystemInfo();
    HardwareAbstractionLayer hardware = sys.getHardware();
    GraphicsCard gpu = hardware.getGraphicsCards().get(1);
    CentralProcessor cpu = hardware.getProcessor();
    CentralProcessor.ProcessorIdentifier cpuID =  cpu.getProcessorIdentifier();

    CPU cpuInst = new CPU(cpuID.getName(),cpu.getPhysicalProcessorCount(),cpu.getLogicalProcessorCount());
    System.out.println(cpuInst.getName() + " " + cpuInst.getLogicalCores() + " " + cpuInst.getPhysicalCores());

}
}
