package laba1;

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
        double[][] DBMDMT = new double[n][n];
        double[][] MDMT = new double[n][n];
        double[][] MZME = new double[n][n];
        double[][] maxMDMT = new double[n][n];
        double[] DB = new double[n];
        double maxDB;

        ArrayHelper.SumVectors(D, B, DB);
        ArrayHelper.MatrixScalar(ME, a, MEa);
        maxDB = ArrayHelper.MaxVector(DB);
        ArrayHelper.MatrixScalar(MD, maxDB, maxMDMT);

        RunMatrixFullMultiply MDMTRun = new RunMatrixFullMultiply(maxMDMT, MT, MDMT);
        RunMatrixFullMultiply MZMERun = new RunMatrixFullMultiply(MZ, MEa, MZME);

        try {
            MDMTRun.t.join();
            MZMERun.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }

        ArrayHelper.MinusMatrix(MDMT, MZME, MA);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba1.txt", "MA", MA);
    }
}
