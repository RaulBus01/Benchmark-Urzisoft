package com.example.backend.RAM;

import com.example.backend.timing.Timer;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RamBenchmark_Test {

    private double score = 0;

    private String prefix;
    private String suffix = ".js";
    private int minIndex = 0;
    private int maxIndex = 8;
    private long fileSize = 512L * 1024 * 1024; // 256, 512 MB, 1GB // type Long!
    private int bufferSize = 2 * 1024; // 4 KB
    private RamBenchmark bench = new RamBenchmark();
    private ArrayList<Double> scores = new ArrayList<>(3);

    public void startBenchmark()
    {
        if (isRunningFromJar()) {
            prefix = getJarFileDirectory() + "/";
        } else {
            prefix = "src/main/resources/com/example/frontend_urzisoft/output/";
        }

        for (int j = 0; j < 3; j++) {
            Timer t = new Timer();
            bench.initialize(8);
            try {
                bench.streamWriteFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            t.start();
            bench.run();

            long time = t.stop();
            for (int i = 0; i < 8; i++) {
                String path = prefix + i + ".js";
                File file = new File(path);

                if (file.exists()) {
                    file.delete();
                } else {
                    //System.out.println("File does not exist");
                }
            }
            double timeinsec = (double) time / 1000000000;

            score = bench.getResult() / timeinsec;
            scores.add(score);
        }
        scores.sort(Double::compareTo);
        System.out.println("Scores: " + scores);
    }

    public double getScore() {
        return scores.get(1) / 30 /2;
    }

    public void cancel() {
        try {
            bench.deleteFiles(prefix, suffix, minIndex, maxIndex);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        bench.cancel();
    }

    private boolean isRunningFromJar() {
        return RamBenchmark_Test.class.getResource("RamBenchmark_Test.class").toString().startsWith("jar:");
    }

    private String getJarFileDirectory() {
        String jarPath = RamBenchmark_Test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            return new File(jarPath).getParentFile().getPath();
        } catch (NullPointerException e) {
            throw new RuntimeException("Cannot determine JAR file directory.");
        }
    }

    public void warmUp() {
        if (isRunningFromJar()) {
            prefix = getJarFileDirectory() + "/";
        } else {
            prefix = "src/main/resources/com/example/frontend_urzisoft/output/";
        }

        for (int j = 0; j < 2; j++) {
            Timer t = new Timer();
            bench.initialize(8);
            try {
                bench.streamWriteFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            t.start();
            bench.run();


            for (int i = 0; i < 8; i++) {
                String path = prefix + i + ".js";
                File file = new File(path);

                if (file.exists()) {
                    file.delete();
                } else {
                    //System.out.println("File does not exist");
                }
            }
        }
    }
}
