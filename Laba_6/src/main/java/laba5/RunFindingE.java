package laba5;

import Common.ArrayHelper;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingE implements Callable<double[]> {
    private double[] Z, D, B;
    private double[][] MT;
    private BlockingQueue blockingQueue;

    private int n;

    private ExecutorService executorService;

    public RunFindingE(
            double[] Z,
            double[] D,
            double[] B,
            double[][] MT,
            BlockingQueue blockingQueue) {
        this.Z = Z;
        this.D = D;
        this.B = B;
        this.MT = MT;
        this.blockingQueue = blockingQueue;
        this.n = Z.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[] call() {
        double minZ;
        double[] E = new double[n];
        double[] DMT = new double[n];
        double[] ZD = new double[n];

        Future<double[]> dmtFuture = executorService.submit(new RunVectorMatrixFullMultiply(MT, D));

        minZ = ArrayHelper.MinVector(Z);

        try {
            DMT = dmtFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        for (int i = 0; i < n; i++)
            ZD[i] = minZ * DMT[i];

        ArrayHelper.SumVectors(ZD, B, E);

        String data = InputOutputParallel.CreateOtputStringForVector("E", E);
        try {
            blockingQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return E;
    }
}
