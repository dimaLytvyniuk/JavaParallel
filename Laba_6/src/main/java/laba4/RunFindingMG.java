package laba4;

import Common.ArrayHelper;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingMG implements Callable<double[][]> {
    private double[][] MD, MT, MZ, ME, MM;
    private Lock fileLock;

    int n;
    ExecutorService executorService;

    public RunFindingMG(
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME,
            double[][] MM,
            Lock fileLock) {
        n = MM[0].length;

        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.MM = MM;
        this.fileLock = fileLock;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[][] call() throws Exception {
        double[][] MG = new double[n][n];
        double[][] MEMM = new double[n][n];
        double[][] MTMZ = new double[n][n];
        double[][] MDMTMZ = new double[n][n];

        Future<double[][]> memmmFuture = executorService.submit(new RunMatrixFullMultiply(ME, MM));

        ArrayHelper.PlusMatrix(MT, MZ, MTMZ);
        Future<double[][]> mdmtmzFuture = executorService.submit(new RunMatrixFullMultiply(MD, MTMZ));

        try {
            MEMM = memmmFuture.get();
            MDMTMZ = mdmtmzFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        ArrayHelper.MinusMatrix(MDMTMZ, MEMM, MG);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba4.txt", "MG", MG, fileLock);

        return MG;
    }
}

