package laba4;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

public class RunFindingA implements Callable<double[]> {
    private double[] B, D, E;
    private double[][] MC, MZ, MM;
    private int n;
    private Lock fileLock;

    private ExecutorService executorService;

    public RunFindingA(
            double[] B,
            double[] D,
            double[] E,
            double[][] MC,
            double[][] MZ,
            double[][] MM,
            Lock fileLock)
    {
        this.B = B;
        this.D = D;
        this.E = E.clone();
        this.MC = MC;
        this.MZ = MZ;
        this.MM = MM;
        this.fileLock = fileLock;
        n = B.length;

        executorService = Executors.newFixedThreadPool(2);
    }

    @Override
    public double[] call() throws Exception {
        double[] A = new double[n];
        double[] BMC = new double[n];
        double[] DMZ = new double[n];
        double[] EMM = new double[n];

        Future<double[]> bmcFuture = executorService.submit(new RunVectorMatrixFullMultiply(MC, B));
        Future<double[]> dmzFuture = executorService.submit(new RunVectorMatrixFullMultiply(MZ, D));
        Future<double[]> emmFuture = executorService.submit(new RunVectorMatrixFullMultiply(MM, E));

        try {
            BMC = bmcFuture.get();
            DMZ = dmzFuture.get();
            EMM = emmFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        for (int i = 0; i < n; i++) {
            A[i] = BMC[i] + DMZ[i] + EMM[i];
        }

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba4.txt", "A", A, fileLock);

        return A;
    }
}
