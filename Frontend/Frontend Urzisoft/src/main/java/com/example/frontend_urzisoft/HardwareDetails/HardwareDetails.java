package com.example.frontend_urzisoft.HardwareDetails;


import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class HardwareDetails
{

    private List<RAM> ramList = new ArrayList<RAM>();
    private CPU cpuInst;
    private SystemBoard systemBoard;


public void getHardwareInfo() {

    SystemInfo sys = new SystemInfo();
    OperatingSystem os = sys.getOperatingSystem();
    HardwareAbstractionLayer hardware = sys.getHardware();

    CentralProcessor cpu = hardware.getProcessor();

    CentralProcessor.ProcessorIdentifier cpuID = cpu.getProcessorIdentifier();

    GlobalMemory memory = hardware.getMemory();
    List<PhysicalMemory> ram = memory.getPhysicalMemory();

    Baseboard baseboard = hardware.getComputerSystem().getBaseboard();
    systemBoard = new SystemBoard(baseboard.getManufacturer(),os.getFamily() + " " + os.getVersionInfo(),hardware.getComputerSystem().getModel());
    cpuInst = new CPU(cpuID.getName(), cpu.getPhysicalProcessorCount(), cpu.getLogicalProcessorCount());
    cpuInst.setFrequency(FormatUtil.formatHertz(cpuID.getVendorFreq()));


    for (PhysicalMemory ramItem : ram)
    {
        double  ramCapacity =ramItem.getCapacity()/(1024*1024*1024);
        int totalRam = (int)ramCapacity;
        long ramFrequency = ramItem.getClockSpeed()/1000000;

        ramList.add(new RAM(ramItem.getManufacturer(), ramItem.getMemoryType(), ramItem.getBankLabel(), totalRam+" GB", ramFrequency+ " MHz"));
    }
}

    public List<RAM> getRamList() {
        return ramList;
    }

    public CPU getCpuInst() {
        return cpuInst;
    }

    public SystemBoard getSystemBoard() {
        return systemBoard;
    }
}
