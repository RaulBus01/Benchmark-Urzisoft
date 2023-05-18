package com.example;

import com.example.backend.CPU.FixedPointBenchmark;
import com.example.backend.CPU.ThreadedRoots;
import com.example.backend.CPU.TypeOfOperation;
import com.example.backend.timing.Timer;
import com.example.backend.timing.*;

import java.util.ArrayList;

public class CPU_Benchmark {
    private long scoreMultiThreaded = 0;
    private long scoreSingleThreaded = 0;
    private long scoreTotal = 0;

    public long getScoreTotal() {
        return getScoreMultiThreaded()+getScoreSingleThreaded();
    }

    public long getScoreMultiThreaded() {
        return scoreMultiThreaded;
    }

    public long getScoreSingleThreaded() {
        return scoreSingleThreaded;
    }


    public void runSingle() {
        // INIT
        ArrayList<Long> scoresList_FIXED_POINT_OP = new ArrayList<>();
        Timer timer = new Timer();
        // INIT_FIXED_POINT_OP
        final int n = 1000000000;
        System.out.println("Start");
        long scoreFixedPointOp;
        FixedPointBenchmark fixedPointBenchmark = new FixedPointBenchmark();
        fixedPointBenchmark.initialize(n);

        // FIXED_POINT_OP_WARMUP
        fixedPointBenchmark.warmup();
        for (int i = 0; i < 3; i++) {

            // RUN_ARITHMETIC
            TypeOfOperation operation = TypeOfOperation.ARITHMETIC;
            timer.start();
            fixedPointBenchmark.run(operation);
            long stopTime = timer.stop();
            long stopTimeArithmetic = timer.transformToUnitTime(UnitTime.MILLISECONDS, stopTime);
            long scoreArithmetic = (long) ((double) (n / (10 * stopTimeArithmetic)));
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
            scoreFixedPointOp = (long) (((double) (scoreAssignement) * 0.46) + ((double) (scoreBranching) * 0.15) + ((double) (scoreArithmetic) * 0.39));// fixed-point score
            scoresList_FIXED_POINT_OP.add(scoreFixedPointOp);
        }

        // COMPUTE_FINAL_SCORES


        scoresList_FIXED_POINT_OP.sort(Long::compareTo);

        scoreSingleThreaded = scoresList_FIXED_POINT_OP.get(1) / 5;


    }

    public void runMulti() {
        ArrayList<Long> scoresList_SQ_ROOTS = new ArrayList<>();

        Timer timer = new Timer();
        long scoreRoots;
        long scoreFixedPointOp;
        long stopTimeRoots;


        System.out.println("Start");

        for (int i = 0; i < 3; i++) {
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
        scoresList_SQ_ROOTS.sort(Long::compareTo);
        scoreMultiThreaded = scoresList_SQ_ROOTS.get(1);




    }
}
