package laba4;

import java.util.concurrent.*;

public class RunMatrixFullMultiply implements Callable<double[][]> {
    private double[][] M1;
    private double[][] M2;

    private ExecutorService executorService;

    public RunMatrixFullMultiply(double[][] M1, double[][] M2) {
        this.M1 = M1;
        this.M2 = M2;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[][] call() {
        int n = M1[0].length;
        double[][] MResult = new double[n][n];

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

        return MResult;
    }
}
