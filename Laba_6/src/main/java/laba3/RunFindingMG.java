package laba3;

import Common.ArrayHelper;
import laba2.InputOutput;
import laba2.RunMatrixFullMultiply;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RunFindingMG implements Runnable {
    private double[][] MG, MD, MT, MZ, ME, MM;
    private Semaphore writeSemaphore;

    int n;
    ExecutorService executorService;

    public RunFindingMG(
            double[][] MG,
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME,
            double[][] MM,
            Semaphore writeSemaphore) {
        n = MG[0].length;

        this.MG = MG;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.MM = MM;
        this.writeSemaphore = writeSemaphore;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        double[][] MEMM = new double[n][n];
        double[][] MTMZ = new double[n][n];
        double[][] MDMTMZ = new double[n][n];

        executorService.execute(new RunMatrixFullMultiply(ME, MM, MEMM));

        ArrayHelper.PlusMatrix(MT, MZ, MTMZ);
        executorService.execute(new RunMatrixFullMultiply(MD, MTMZ, MDMTMZ));

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        ArrayHelper.MinusMatrix(MDMTMZ, MEMM, MG);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba3.txt", "MG", MG, writeSemaphore);
    }
}

