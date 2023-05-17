package com.example;

import com.example.backend.CPU.FixedPointBenchmark;
import com.example.backend.CPU.LZWEncoder;
import com.example.backend.CPU.ThreadedRoots;
import com.example.backend.CPU.TypeOfOperation;
import com.example.backend.CPU.timing.Timer;
import com.example.backend.CPU.timing.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static javafx.application.Platform.exit;

public class CPU_Benchmark{
    private  long  scoreMultiThreaded = 0;
    private  long scoreSingleThreaded = 0;
    private  long  scoreTotal = 0;

    public long getScoreTotal() {
        return scoreTotal;
    }
    public long getScoreMultiThreaded() {
        return scoreMultiThreaded;
    }
    public long getScoreSingleThreaded() {
        return scoreSingleThreaded;
    }



    public void run() {
        // INIT
        ArrayList<Long> scoresList_LZW = new ArrayList<>();
        ArrayList<Long> scoresList_SQ_ROOTS = new ArrayList<>();
        ArrayList<Long> scoresList_FIXED_POINT_OP = new ArrayList<>();

            Timer timer = new Timer();
            long scoreLZW;
            long scoreRoots;
            long scoreFixedPointOp;
            long size_of_file = 0;
            long stopTimeRoots;
            long stopTimeLZW = 0;
        // RUN_LZW
            for(int i = 0; i < 2; i++) {
                try {
                    System.out.println("Start");
                    Path path = Paths.get("src/main/resources/in.pdf");

                    size_of_file = Files.size(path);//in bytes



                    timer.start();

                    LZWEncoder.compress(new File(path.toUri()), new File("src/main/resources/out.txt"));
                    System.out.println("Stop");
                    stopTimeLZW = timer.stop();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Error");
                }
                // COMPUTE_SCORE_LZW
                System.out.println("Start");
                stopTimeLZW = timer.transformToUnitTime(UnitTime.SECONDS, stopTimeLZW);

                scoreLZW = (long) ((double) (size_of_file / (stopTimeLZW *10 +1) ));
                System.out.println(scoreLZW);
                scoresList_LZW.add(scoreLZW);

       }
            System.out.println("Start");
            // RUN_ROOTS
            for(int i = 0; i < 2; i++) {
                long SystemThreadCount = Runtime.getRuntime().availableProcessors();
                ThreadedRoots threadedRoots = new ThreadedRoots();
                threadedRoots.initialize(1000000000L);
                threadedRoots.warmup(SystemThreadCount);
                timer.start();
                threadedRoots.run(SystemThreadCount);
                stopTimeRoots = timer.stop();
                // COMPUTE_SCORE_ROOTS
                long scoreTime = timer.transformToUnitTime(UnitTime.MILLISECONDS, stopTimeRoots);
                scoreRoots = (long) ((double) (1000000000L / (scoreTime * SystemThreadCount)));
                scoresList_SQ_ROOTS.add(scoreRoots);
//                logger.write("Done SQUARE_ROOTS" + " score: " + scoreRoots + "\n");
            }
            // INIT_FIXED_POINT_OP
            final int n = 1000000000;
            FixedPointBenchmark fixedPointBenchmark = new FixedPointBenchmark();
            fixedPointBenchmark.initialize(n);

            // FIXED_POINT_OP_WARMUP
            fixedPointBenchmark.warmup();
            for(int i = 0; i <2; i++) {

                // RUN_ARITHMETIC
                TypeOfOperation operation = TypeOfOperation.ARITHMETIC;
                timer.start();
                fixedPointBenchmark.run(operation);
                long stopTime = timer.stop();
                long stopTimeArithmetic = timer.transformToUnitTime(UnitTime.MILLISECONDS, stopTime);
                long scoreArithmeic = (long) ((double) (n / (10 * stopTimeArithmetic)));
                // RUN_BRANCHING
                operation = TypeOfOperation.BRANCHING;
                timer.start();
                fixedPointBenchmark.run(operation);
                stopTime = timer.stop();
                long stopTimeBranching = timer.transformToUnitTime(UnitTime.MILLISECONDS, stopTime);
                long scoreBranching = (long) ((double) (n / (100 * stopTimeBranching)));
                // RUN_ASSIGNMENT
                operation = TypeOfOperation.ASSIGNMENT;
                timer.start();
                fixedPointBenchmark.run(operation);
                stopTime = timer.stop();
                long stopTimeAssignement = timer.transformToUnitTime(UnitTime.MILLISECONDS, stopTime);
                long scoreAssignement = (long) ((double) (n / (10 * stopTimeAssignement)));
                // COMPUTE_SCORE_FIXED_POINT_OP
                scoreFixedPointOp = (long) (((double) (scoreAssignement) * 0.46) + ((double) (scoreBranching) * 0.15) + ((double) (scoreArithmeic) * 0.39));// fixed-point score
                scoresList_FIXED_POINT_OP.add(scoreFixedPointOp);
            }
            System.out.println("Done");
            // COMPUTE_FINAL_SCORES
            scoresList_LZW.sort(Long::compareTo);
             scoresList_SQ_ROOTS.sort(Long::compareTo);

            scoresList_FIXED_POINT_OP.sort(Long::compareTo);

           scoreSingleThreaded = scoresList_FIXED_POINT_OP.get(1);

            System.out.println("Done3");
            scoreMultiThreaded = (scoresList_SQ_ROOTS.get(1)) ;
            System.out.println(scoreMultiThreaded);
            System.out.println("Done4");
            System.out.println(scoreSingleThreaded);
            //scoreTotal = (scoreMultiThreaded + scoreSingleThreaded) / 2;
            System.out.println("Score done");
    }
    public void cancel()
    {
        exit();
    }
}
