package laba3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RunMatrixFullMultiply implements Runnable {
    private double[][] M1;
    private double[][] M2;
    private double[][] MResult;

    private ExecutorService executorService;

    public RunMatrixFullMultiply(double[][] M1, double[][] M2, double[][] MResult) {
        this.M1 = M1;
        this.M2 = M2;
        this.MResult = MResult;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        Semaphore firstSemaphore = new Semaphore(1);
        Semaphore secondSemaphore = new Semaphore(1);

        executorService.execute(new RunMatrixHalfMultiply(M1, M2, MResult, true, firstSemaphore, secondSemaphore));
        executorService.execute(new RunMatrixHalfMultiply(M1, M2, MResult, false, secondSemaphore, firstSemaphore));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
