package laba4;

import Common.Data;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Laba4 {
    private static final String fileName = "laba4.txt";

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

        Lock fileLock = new ReentrantLock();
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
        inputOutput.OutputToFileVector(fileName, "B", B, fileLock);
        inputOutput.OutputToFileVector(fileName, "D", D, fileLock);
        inputOutput.OutputToFileVector(fileName, "Z", Z, fileLock);

        inputOutput.OutputToFileMatrix(fileName, "MC", MC, fileLock);
        inputOutput.OutputToFileMatrix(fileName, "MZ", MZ, fileLock);
        inputOutput.OutputToFileMatrix(fileName, "MM", MM, fileLock);
        inputOutput.OutputToFileMatrix(fileName, "MD", MD, fileLock);
        inputOutput.OutputToFileMatrix(fileName, "MT", MT, fileLock);
        inputOutput.OutputToFileMatrix(fileName, "ME", ME, fileLock);

        Future<double[]> aFuture = executorService.submit(new RunFindingA(B, D, E, MC, MZ, MM, fileLock));
        Future<double[][]> mgFuture = executorService.submit(new RunFindingMG(MD, MT, MZ, ME, MM, fileLock));
        Future<double[][]> maFuture = executorService.submit(new RunFindingMA(a, D, B, MD, MT, MZ, ME, fileLock));
        Future<double[]> eFuture = executorService.submit(new RunFindingE(Z, D, B, MT, fileLock));

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
    }
}
