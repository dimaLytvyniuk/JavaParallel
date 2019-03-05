package laba1;

import Common.ArrayHelper;

public class RunFindingMG implements Runnable {
    private double[][] MG, MD, MT, MZ, ME, MM;
    int n;
    Thread t;

    public RunFindingMG(
            double[][] MG,
            double[][] MD,
            double[][] MT,
            double[][] MZ,
            double[][] ME,
            double[][] MM) {
        n = MG[0].length;

        this.MG = MG;
        this.MD = MD;
        this.MT = MT;
        this.MZ = MZ;
        this.ME = ME;
        this.MM = MM;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double[][] MEMM = new double[n][n];
        double[][] MTMZ = new double[n][n];
        double[][] MDMTMZ = new double[n][n];

        RunMatrixFullMultiply MEMMRun = new RunMatrixFullMultiply(ME, MM, MEMM);

        ArrayHelper.PlusMatrix(MT, MZ, MTMZ);
        RunMatrixFullMultiply MTMZRun = new RunMatrixFullMultiply(MD, MTMZ, MDMTMZ);

        try {
            MEMMRun.t.join();
            MTMZRun.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }

        ArrayHelper.MinusMatrix(MDMTMZ, MEMM, MG);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba1.txt", "MG", MG);
    }
}

