package laba5;

import Common.ArrayHelper;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingMA implements Callable<double[][]> {
    private double[] D, B;
    private double[][] MD, MT, MZ, ME;
    private double a;
    private BlockingQueue blockingQueue;

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
            BlockingQueue blockingQueue) {
        this.a = a;
        this.D = D;
        this.B = B;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.blockingQueue = blockingQueue;
        n = D.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[][] call() {
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

        String data = InputOutputParallel.CreateOtputStringForMatrix("MA", MA);
        try {
            blockingQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return MA;
    }
}
