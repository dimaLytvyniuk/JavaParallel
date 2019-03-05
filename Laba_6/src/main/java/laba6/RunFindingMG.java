package laba6;

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
        double[][] MTMZ = new double[n][n];

        ArrayHelper.PlusMatrix(MT, MZ, MTMZ);

        double[][] MEMM = RunMatrixMultiply.startForkJoinMultiply(ME, MM);
        double[][] MDMTMZ = RunMatrixMultiply.startForkJoinMultiply(MD, MTMZ);

        ArrayHelper.MinusMatrix(MDMTMZ, MEMM, MG);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileMatrix("laba6.txt", "MG", MG);
    }
}

