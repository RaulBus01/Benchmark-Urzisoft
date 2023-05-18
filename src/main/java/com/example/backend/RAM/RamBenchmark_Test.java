package com.example.backend.RAM;

import  com.example.backend.timing.Timer;
import java.io.IOException;
public class RamBenchmark_Test {

    private double score = 0;
    public void startBenchmark(){
        RamBenchmark bench = new RamBenchmark();
        Timer t = new Timer();
        bench.initialize();
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
