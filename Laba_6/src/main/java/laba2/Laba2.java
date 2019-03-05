package laba2;

import Common.Data;

import java.util.concurrent.Semaphore;

public class Laba2 {
    private static final String fileName = "laba2.txt";

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

        Semaphore writeSemaphore = new Semaphore(1);

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
        inputOutput.OutputToFileVector(fileName, "B", B, writeSemaphore);
        inputOutput.OutputToFileVector(fileName, "D", D, writeSemaphore);
        inputOutput.OutputToFileVector(fileName, "Z", Z, writeSemaphore);

        inputOutput.OutputToFileMatrix(fileName, "MC", MC, writeSemaphore);
        inputOutput.OutputToFileMatrix(fileName, "MZ", MZ, writeSemaphore);
        inputOutput.OutputToFileMatrix(fileName, "MM", MM, writeSemaphore);
        inputOutput.OutputToFileMatrix(fileName, "MD", MD, writeSemaphore);
        inputOutput.OutputToFileMatrix(fileName, "MT", MT, writeSemaphore);
        inputOutput.OutputToFileMatrix(fileName, "ME", ME, writeSemaphore);

        RunFindingA runA = new RunFindingA(A, B, D, E, MC, MZ, MM, writeSemaphore);
        RunFindingMG runMG = new RunFindingMG(MG, MD, MT, MZ, ME, MM, writeSemaphore);
        RunFindingMA runMA = new RunFindingMA(a, D, B, MA, MD, MT, MZ, ME, writeSemaphore);
        RunFindingE runE = new RunFindingE(E, Z, D, B, MT, writeSemaphore);

        try {
            runA.t.join();
            runMG.t.join();
            runMA.t.join();
            runE.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }
    }
}
