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

    CentralProcessor.ProcessorIdentifier cpuID = cpu.getProcessorIdentifier();

    GlobalMemory memory = hardware.getMemory();
    List<PhysicalMemory> ram = memory.getPhysicalMemory();
    List<RAM> ramList = new ArrayList<RAM>();
    for (PhysicalMemory ramItem : ram)
    {
        ramList.add(new RAM(ramItem.getManufacturer(), ramItem.getMemoryType(), ramItem.getBankLabel(), FormatUtil.formatBytesDecimal(ramItem.getCapacity()), FormatUtil.formatHertz(ramItem.getClockSpeed())));
    }
    CPU cpuInst = new CPU(cpuID.getName(), cpu.getPhysicalProcessorCount(), cpu.getLogicalProcessorCount());

    for (RAM ramItem : ramList)
    {
        System.out.println(ramItem.getManufacturer() + " " + ramItem.getMemoryType() + " " + ramItem.getBankLabel() + " " + ramItem.getGetCapacity() + " " + ramItem.getFrequency());
    }
    // System.out.println(cpuInst.getName() + " " + cpuInst.getLogicalCores() + " " + cpuInst.getPhysicalCores());

    Baseboard baseboard = hardware.getComputerSystem().getBaseboard();
    System.out.println(baseboard.getManufacturer()+"\n" + hardware.getComputerSystem().getModel() + "\n" + baseboard.getVersion() + "\n" + baseboard.getSerialNumber());
}
}
