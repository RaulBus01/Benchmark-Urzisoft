package com.example.frontend_urzisoft;

import com.example.frontend_urzisoft.HardwareDetails.CPU;
import com.example.frontend_urzisoft.HardwareDetails.RAM;
import com.example.frontend_urzisoft.HardwareDetails.SYI;

import com.example.frontend_urzisoft.HardwareDetails.SystemBoard;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.List;

public class ComponentsService {

private  SystemInfo sys;
private  OperatingSystem os;
private  HardwareAbstractionLayer hardware;
private  CentralProcessor cpu;
private  CentralProcessor.ProcessorIdentifier cpuID;
private  GlobalMemory memory;
private  List<PhysicalMemory> ram;
private Baseboard baseboard;

public ComponentsService(){
    this.sys = new SystemInfo();
    this.os = sys.getOperatingSystem();
    this.hardware = sys.getHardware();
    this.cpu = hardware.getProcessor();
    this.cpuID = cpu.getProcessorIdentifier();
    this.memory = hardware.getMemory();
    this.ram = memory.getPhysicalMemory();
    this.baseboard = hardware.getComputerSystem().getBaseboard();
}

    public CPU createCPU()
    {
        CPU cpu = new CPU(this.cpuID.getName(), this.cpu.getPhysicalProcessorCount(), this.cpu.getLogicalProcessorCount(), FormatUtil.formatHertz(this.cpuID.getVendorFreq()));
        return cpu;
    }

    public List<RAM> createRam()
    {
        List<RAM> ram = null;
        for (PhysicalMemory ramItem : this.ram)
        {
            int totalRam = (int)ramItem.getCapacity()/(1024*1024*1024);
            long ramFrequency = ramItem.getClockSpeed()/1000000;
            int index = 0;
            ram.add(new RAM(ramItem.getManufacturer(), ramItem.getBankLabel(),totalRam + " GB", ramFrequency + " MHz", index+1));
        }
        return ram;
    }

    public SYI createSYI()
    {
        String oSystem = os.getFamily() + " " + os.getVersionInfo();
        SYI syi = new SYI(oSystem, hardware.getComputerSystem().getModel(), baseboard.getManufacturer());
        return syi;
    }
}
