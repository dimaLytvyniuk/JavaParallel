package laba6;

import Common.ArrayHelper;

public class RunFindingMA implements Runnable {
    private double[] D, B;
    private double[][] MA, MD, MT, MZ, ME;
    private double a;
    private int n;

    Thread t;

    public RunFindingMA(
            double a,
            double[] D,
            double[] B,
            double[][] MA,
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME) {
        this.a = a;
        this.D = D;
        this.B = B;
        this.MA = MA;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        n = D.length;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double[][] MEa = new double[n][n];
        double[][] maxMDMT = new double[n][n];
        double[] DB = new double[n];
        double maxDB;

        ArrayHelper.SumVectors(D, B, DB);
        ArrayHelper.MatrixScalar(ME, a, MEa);
        maxDB = ArrayHelper.MaxVector(DB);
        ArrayHelper.MatrixScalar(MD, maxDB, maxMDMT);

        double[][] MDMT = RunMatrixMultiply.startForkJoinMultiply(maxMDMT, MT);
        double[][] MZME = RunMatrixMultiply.startForkJoinMultiply(MZ, MEa);

        ArrayHelper.MinusMatrix(MDMT, MZME, MA);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba6.txt", "MA", MA);
    }
}
