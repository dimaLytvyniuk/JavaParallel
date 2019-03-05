package laba3;

import laba2.InputOutput;
import laba2.RunVectorMatrixFullMultiply;

import java.util.concurrent.*;

public class RunFindingA implements Runnable {
    private double[] A, B, D, E;
    private double[][] MC, MZ, MM;
    private int n;
    private Semaphore writeSemaphore;
    private ExecutorService executorService;

    public RunFindingA(
            double[] A,
            double[] B,
            double[] D,
            double[] E,
            double[][] MC,
            double[][] MZ,
            double[][] MM,
            Semaphore writeSemaphore)
    {
        this.A = A;
        this.B = B;
        this.D = D;
        this.E = E.clone();
        this.MC = MC;
        this.MZ = MZ;
        this.MM = MM;
        this.writeSemaphore = writeSemaphore;
        n = A.length;

        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        double[] BMC = new double[n];
        double[] DMZ = new double[n];
        double[] EMM = new double[n];

        executorService.execute(new RunVectorMatrixFullMultiply(MC, B, BMC));
        executorService.execute(new RunVectorMatrixFullMultiply(MZ, D, DMZ));
        executorService.execute(new RunVectorMatrixFullMultiply(MM, E, EMM));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < n; i++) {
            A[i] = BMC[i] + DMZ[i] + EMM[i];
        }

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba3.txt", "A", A, writeSemaphore);
    }
}
