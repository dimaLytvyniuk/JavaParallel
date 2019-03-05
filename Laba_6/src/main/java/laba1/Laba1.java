package laba1;

import Common.Data;

public class Laba1 {
    private static final String fileName = "laba1.txt";

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

        RunFindingA runA = new RunFindingA(A, B, D, E, MC, MZ, MM);
        RunFindingMG runMG = new RunFindingMG(MG, MD, MT, MZ, ME, MM);
        RunFindingMA runMA = new RunFindingMA(a, D, B, MA, MD, MT, MZ, ME);
        RunFindingE runE = new RunFindingE(E, Z, D, B, MT);

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
