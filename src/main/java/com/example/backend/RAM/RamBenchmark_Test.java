package com.example.backend.RAM;

import  com.example.backend.timing.Timer;
import java.io.IOException;
public class RamBenchmark_Test {

    private double score = 0;
    private String prefix = "C:\\Users\\Andre\\Desktop\\Irian\\hello";
    private String suffix = ".js";
    private int minIndex = 0;
    private int maxIndex = 8;
    private long fileSize = 512*1024*1024; // 256, 512 MB, 1GB // type Long!
    private int bufferSize = 2*1024; // 4 KB
    public void startBenchmark(){
        RamBenchmark bench = new RamBenchmark();
        Timer t = new Timer();
        bench.initialize(8);
        try {
            bench.streamWriteFixedFileSize(prefix, suffix, minIndex,
                    maxIndex, fileSize, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        t.start();
        bench.run();
        long time = t.stop();
        double timeinsec = (double)time / 1000000000;

        score = bench.getResult() / (double)timeinsec;
        System.out.println("Score: " + score);
    }

    public double getScore() {
        return score;
    }


}
