package laba5;

import Common.Data;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Laba5 {
    private static final String fileName = "laba5.txt";

    public static void run()
    {
        int n = 13;
        double[] A = new double[n],
                B = new double[n],
                D = new double[n],
                E = new double[n],
                Z = new double[n];
        double[][] MC = new double[n][n],
                MZ = new double[n][n],
                MM = new double[n][n],
                MD = new double[n][n],
                MG = new double[n][n],
                MT = new double[n][n],
                ME = new double[n][n],
                MA = new double[n][n];
        double a = 1;

        BlockingQueue blockingQueue = new ArrayBlockingQueue(16);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Data.generateVector(B);
        Data.generateVector(D);
        Data.generateVector(Z);

        Data.generateMatrix(MC);
        Data.generateMatrix(MZ);
        Data.generateMatrix(MM);
        Data.generateMatrix(MD);
        Data.generateMatrix(MT);
        Data.generateMatrix(ME);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector(fileName, "B", B);
        inputOutput.OutputToFileVector(fileName, "D", D);
        inputOutput.OutputToFileVector(fileName, "Z", Z);

        inputOutput.OutputToFileMatrix(fileName, "MC", MC);
        inputOutput.OutputToFileMatrix(fileName, "MZ", MZ);
        inputOutput.OutputToFileMatrix(fileName, "MM", MM);
        inputOutput.OutputToFileMatrix(fileName, "MD", MD);
        inputOutput.OutputToFileMatrix(fileName, "MT", MT);
        inputOutput.OutputToFileMatrix(fileName, "ME", ME);

        Future<double[]> aFuture = executorService.submit(new RunFindingA(B, D, E, MC, MZ, MM, blockingQueue));
        Future<double[][]> mgFuture = executorService.submit(new RunFindingMG(MD, MT, MZ, ME, MM, blockingQueue));
        Future<double[][]> maFuture = executorService.submit(new RunFindingMA(a, D, B, MD, MT, MZ, ME, blockingQueue));
        Future<double[]> eFuture = executorService.submit(new RunFindingE(Z, D, B, MT, blockingQueue));
        executorService.execute(new InputOutputParallel(blockingQueue));

        try {
            A = aFuture.get();
            MG = mgFuture.get();
            MA = maFuture.get();
            E = eFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
