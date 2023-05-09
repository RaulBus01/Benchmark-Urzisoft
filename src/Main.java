import CPU.FixedPointBenchmark;
import CPU.LZWEncoder;
import CPU.ThreadedRoots;
import CPU.TypeOfOperation;
import timing.Timer;
import timing.UnitTime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CPU_Benchmark{
    private static long  scoreMultiThreaded = 0;
    private static long scoreSingleThreaded = 0;
    private static long  scoreTotal = 0;

    public long getScoreTotal() {
        return scoreTotal;
    }
    public long getScoreMultiThreaded() {
        return scoreMultiThreaded;
    }
    public long getScoreSingleThreaded() {
        return scoreSingleThreaded;
    }

    public static void run(String[] args) {
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
            for(int i = 0; i < 5; i++) {
                try {
                    Path path = Paths.get("C:\\Users\\HP\\Desktop\\in.pdf");
                    size_of_file = Files.size(path);//in bytes
                    timer.start();
                    LZWEncoder.compress(new File(path.toUri()), new File("C:\\Users\\HP\\Desktop\\output.txt"));
                    stopTimeLZW = timer.stop();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                // COMPUTE_SCORE_LZW
                stopTimeLZW = timer.transformToUnitTime(UnitTime.SECONDS, stopTimeLZW);
                scoreLZW = (long) ((double) (size_of_file / (stopTimeLZW * 10)));
                scoresList_LZW.add(scoreLZW);
            }
            // RUN_ROOTS
            for(int i = 0; i < 5; i++) {
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
            for(int i = 0; i < 5; i++) {

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
            // COMPUTE_FINAL_SCORES
            scoresList_LZW.sort(Long::compareTo);
            scoresList_SQ_ROOTS.sort(Long::compareTo);
            scoresList_FIXED_POINT_OP.sort(Long::compareTo);
            scoreSingleThreaded = scoresList_FIXED_POINT_OP.get(2);
            scoreMultiThreaded = (scoresList_LZW.get(2) + scoresList_SQ_ROOTS.get(2)) / 2;
            scoreTotal = (scoreMultiThreaded + scoreSingleThreaded) / 2;
    }
}
