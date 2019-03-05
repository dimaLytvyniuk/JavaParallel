package laba3;

import Common.ArrayHelper;
import laba2.InputOutput;
import laba2.RunMatrixFullMultiply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RunFindingMA implements Runnable {
    private double[] D, B;
    private double[][] MA, MD, MT, MZ, ME;
    private double a;
    private Semaphore writeSemaphore;

    private int n;

    private ExecutorService executorService;

    public RunFindingMA(
            double a,
            double[] D,
            double[] B,
            double[][] MA,
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME,
            Semaphore writeSemaphore) {
        this.a = a;
        this.D = D;
        this.B = B;
        this.MA = MA;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.writeSemaphore = writeSemaphore;
        n = D.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        double[][] MEa = new double[n][n];
        double[][] MDMT = new double[n][n];
        double[][] MZME = new double[n][n];
        double[][] maxMDMT = new double[n][n];
        double[] DB = new double[n];
        double maxDB;

        ArrayHelper.SumVectors(D, B, DB);
        ArrayHelper.MatrixScalar(ME, a, MEa);
        maxDB = ArrayHelper.MaxVector(DB);
        ArrayHelper.MatrixScalar(MD, maxDB, maxMDMT);

        executorService.execute(new RunMatrixFullMultiply(maxMDMT, MT, MDMT));
        executorService.execute(new RunMatrixFullMultiply(MZ, MEa, MZME));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        ArrayHelper.MinusMatrix(MDMT, MZME, MA);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba3.txt", "MA", MA, writeSemaphore);
    }
}
