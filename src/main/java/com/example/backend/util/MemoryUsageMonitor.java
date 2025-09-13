package com.example.backend.util;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class MemoryUsageMonitor implements Runnable{

    public static final OperatingSystem OS = new SystemInfo().getOperatingSystem();

    private static final int SAMPLE_RATE_MS = 500;

    private final int processID;
    private volatile long peakMemoryUsage = 0;

    public MemoryUsageMonitor(int processID){
        this.processID = processID;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            OSProcess process = OS.getProcess(processID);
            if(process == null){
                break;
            }
            long currentMemoryUsage = process.getResidentSetSize();
            if(currentMemoryUsage > peakMemoryUsage){
                peakMemoryUsage = currentMemoryUsage;
            }
            try {
                Thread.sleep(SAMPLE_RATE_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public long getPeakMemoryUsage(){
        return peakMemoryUsage;
    }

}