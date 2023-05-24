package com.example.backend.RAM;

import  com.example.backend.timing.Timer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class RamBenchmark_Test {

    private double score = 0;

    private String prefix = "src/main/resources/com/example/frontend_urzisoft/output/";
    private String suffix = ".js";
    private int minIndex = 0;
    private int maxIndex = 8;
    private long fileSize = 512*1024*1024; // 256, 512 MB, 1GB // type Long!
    private int bufferSize = 2*1024; // 4 KB
    private RamBenchmark bench = new RamBenchmark();
    public void startBenchmark(){

        Timer t = new Timer();
        bench.initialize(8);
        try {

            bench.streamWriteFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, true);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        t.start();
        bench.run();

        long time = t.stop();
        for(int i = 0; i < 8; i++)
        {
            String path = "src/main/resources/com/example/frontend_urzisoft/output/" + Integer.toString(i) + ".js";

            File file = Path.of(path).toFile();

            file.delete();



        }
        double timeinsec = (double)time / 1000000000;

        score = bench.getResult() / (double)timeinsec;

    }

    public double getScore() {
        return score/5.5;
    }

    public void cancel(){

        bench.cancel();
    }


}
