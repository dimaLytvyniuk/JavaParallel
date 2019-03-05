package laba3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RunVectorMatrixFullMultiply implements Runnable {
    private double[][] M;
    private double[] V;
    private double[] VResult;

    private ExecutorService executorService;

    public RunVectorMatrixFullMultiply(double[][] M, double[] V, double[] VResult) {
        this.M = M;
        this.V = V;
        this.VResult = VResult;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        Semaphore firstSemaphore = new Semaphore(1);
        Semaphore secondSemaphore = new Semaphore(1);

        executorService.execute(new RunVectorsMatrixHalfMultiply(M, V, VResult, true, firstSemaphore, secondSemaphore));
        executorService.execute(new RunVectorsMatrixHalfMultiply(M, V, VResult, false, secondSemaphore, firstSemaphore));

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
