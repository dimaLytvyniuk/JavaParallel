package laba3;

import Common.ArrayHelper;
import laba2.InputOutput;
import laba2.RunVectorMatrixFullMultiply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RunFindingE implements Runnable {
    private double[] Z, D, B, E;
    private double[][] MT;
    private Semaphore writeSemaphore;

    private int n;

    private ExecutorService executorService;

    public RunFindingE(
            double[] E,
            double[] Z,
            double[] D,
            double[] B,
            double[][] MT,
            Semaphore writeSemaphore) {
        this.E = E;
        this.Z = Z;
        this.D = D;
        this.B = B;
        this.MT = MT;
        this.writeSemaphore = writeSemaphore;
        this.n = Z.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        double minZ;
        double[] DMT = new double[n];
        double[] ZD = new double[n];

        executorService.execute(new RunVectorMatrixFullMultiply(MT, D, DMT));

        minZ = ArrayHelper.MinVector(Z);

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        for (int i = 0; i < n; i++)
            ZD[i] = minZ * DMT[i];

        ArrayHelper.SumVectors(ZD, B, E);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba3.txt", "E", E, writeSemaphore);

    }
}
