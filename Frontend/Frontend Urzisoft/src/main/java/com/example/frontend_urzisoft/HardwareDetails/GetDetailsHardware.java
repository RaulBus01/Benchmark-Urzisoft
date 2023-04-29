package com.example.frontend_urzisoft.HardwareDetails;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.util.FormatUtil;

public class GetDetailsHardware
{
public void getHardwareInfo() {
        // Retrieve the operating system MXBean
    SystemInfo sys = new SystemInfo();
    HardwareAbstractionLayer hardware = sys.getHardware();

    CentralProcessor cpu = hardware.getProcessor();
    CentralProcessor.ProcessorIdentifier cpuID =  cpu.getProcessorIdentifier();

    GlobalMemory memory = hardware.getMemory();
    List<PhysicalMemory> ram = memory.getPhysicalMemory();

    for(PhysicalMemory ramSlot : ram)
    {
        System.out.println(ramSlot.getCapacity());
    }
    CPU cpuInst = new CPU(cpuID.getName(),cpu.getPhysicalProcessorCount(),cpu.getLogicalProcessorCount());
   // System.out.println(cpuInst.getName() + " " + cpuInst.getLogicalCores() + " " + cpuInst.getPhysicalCores());

}
}
