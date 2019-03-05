package laba4;

import java.util.concurrent.*;

public class RunVectorMatrixFullMultiply implements Callable<double[]> {
    private double[][] M;
    private double[] V;

    private ExecutorService executorService;

    public RunVectorMatrixFullMultiply(double[][] M, double[] V) {
        this.M = M;
        this.V = V;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[] call() {
        int n = V.length;
        double[] VResult = new double[n];

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

        return VResult;
    }
}
