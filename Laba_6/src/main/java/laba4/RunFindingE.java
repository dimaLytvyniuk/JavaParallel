package laba4;

import Common.ArrayHelper;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingE implements Callable<double[]> {
    private double[] Z, D, B;
    private double[][] MT;
    private Lock fileLock;

    private int n;

    private ExecutorService executorService;

    public RunFindingE(
            double[] Z,
            double[] D,
            double[] B,
            double[][] MT,
            Lock fileLock) {
        this.Z = Z;
        this.D = D;
        this.B = B;
        this.MT = MT;
        this.fileLock = fileLock;
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

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba4.txt", "E", E, fileLock);

        return E;
    }
}
