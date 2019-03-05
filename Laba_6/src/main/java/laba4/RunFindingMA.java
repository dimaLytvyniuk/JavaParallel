package laba4;

import Common.ArrayHelper;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingMA implements Callable<double[][]> {
    private double[] D, B;
    private double[][] MD, MT, MZ, ME;
    private double a;
    private Lock fileLock;

    private int n;

    private ExecutorService executorService;

    public RunFindingMA(
            double a,
            double[] D,
            double[] B,
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME,
            Lock fileLock) {
        this.a = a;
        this.D = D;
        this.B = B;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.fileLock = fileLock;
        n = D.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[][] call() throws Exception {
        double[][] MA = new double[n][n];
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

        Future<double[][]> mdmtFuture = executorService.submit(new RunMatrixFullMultiply(maxMDMT, MT));
        Future<double[][]> mzmeFuture = executorService.submit(new RunMatrixFullMultiply(MZ, MEa));

        try {
            MDMT = mdmtFuture.get();
            MZME = mzmeFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        ArrayHelper.MinusMatrix(MDMT, MZME, MA);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba4.txt", "MA", MA, fileLock);

        return MA;
    }
}
