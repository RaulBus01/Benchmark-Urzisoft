package com.example.backend.CPU;

public class ThreadedRoots {

    private double result;
    private long size;
    private boolean running;
    private boolean canceled = false;


    public void initialize(Object... params) {
        if(params[0] instanceof Long) {
            size = (long) params[0];
        }
    }
    public void cancel(){
        canceled = true;
    }

    public void run(Object... options) {
        long nThreads = 1;
        if(options[0] instanceof Long) {
            if((long) options[0] > 0) {
                nThreads = (long) options[0];
            }
        }

        Thread[] threads = new Thread[(int)nThreads];
        final long jobPerThread = size/nThreads;
        running = true; // flag used to stop all started threads
        for (int i = 0; i < nThreads && !canceled; ++i) {
            threads[i] = new Thread(new SquareRootTask(i * jobPerThread, (i + 1) * jobPerThread));
            threads[i].start();
        }

        // join threads
        for (int i = 0; i < nThreads && !canceled; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            running = false;
        }
    }
    public void warmup(long threads) {
        run(threads);
    }

    public String getResult() {
        return String.valueOf(result);
    }



    class SquareRootTask implements Runnable {

        private final long from;
        private final long to;
        private final double precision = 1e-4; // fixed
        private double result = 0.0;

        public SquareRootTask(long from, long to) {
            this.from = from;
            this.to = to;
        }
        public void run() {
            for(long i = from; i < to && running && !canceled; ++i) {
                result += getNewtonian(i);
            }
        }
        private double getNewtonian(double x) {
            double S = x;
            while (Math.abs(Math.pow(S,2)-x) > precision) {
                S = (S + x/S) / 2;
            }

            return S;
        }
    }
}

