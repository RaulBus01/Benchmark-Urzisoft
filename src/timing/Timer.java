package timing;

public class Timer implements ITimer{
    private boolean isRunning = false;
    private boolean isPaused = false;
    private long totalTime = 0;
    private long elapsedTime = 0;
    private long startTime = 0;

    @Override
    public void start() {
        if(!isRunning){
             startTime = System.nanoTime();
             totalTime = 0;
             elapsedTime = 0;
             isRunning = true;
             isPaused = false;
        }
    }

    @Override
    public long stop()
    {
        if(isRunning && !isPaused){
             elapsedTime = System.nanoTime() - startTime;
             totalTime += elapsedTime;
             isRunning = false;
             return totalTime;
        }
        return 0;
    }

    @Override
    public void resume() {
        if(isPaused && isRunning) {
            startTime = System.nanoTime();
            elapsedTime = 0;
            isPaused = false;
        }
    }

    @Override
    public long pause() {
        if(isRunning && !isPaused){
            elapsedTime = System.nanoTime() - startTime;
            totalTime += elapsedTime;
            isPaused = true;
            return elapsedTime;
        }
        return 0;
    }

    @Override
    public long transformToUnitTime(UnitTime tUnit, long t) {
        return (long) ((double)t/tUnit.getValue());
    }

}
