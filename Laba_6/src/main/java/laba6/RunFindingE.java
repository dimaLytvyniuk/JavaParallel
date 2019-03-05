package laba6;

import Common.ArrayHelper;

public class RunFindingE implements Runnable {
    private double[] Z, D, B, E;
    private double[][] MT;
    private int n;

    Thread t;

    public RunFindingE(
            double[] E,
            double[] Z,
            double[] D,
            double[] B,
            double[][] MT) {
        this.E = E;
        this.Z = Z;
        this.D = D;
        this.B = B;
        this.MT = MT;
        this.n = Z.length;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double minZ;
        double[] DMT = RunMatrixVectorMultiply.startForkJoinMultiply(MT, D);
        double[] ZD = new double[n];

        minZ = ArrayHelper.MinVector(Z);

        for (int i = 0; i < n; i++)
            ZD[i] = minZ * DMT[i];

        ArrayHelper.SumVectors(ZD, B, E);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba6.txt", "E", E);

    }
}
