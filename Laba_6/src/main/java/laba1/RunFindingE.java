package laba1;

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
        double[] DMT = new double[n];
        double[] ZD = new double[n];

        RunVectorMatrixFullMultiply DMTRunner = new RunVectorMatrixFullMultiply(MT, D, DMT);

        minZ = ArrayHelper.MinVector(Z);

        try {
            DMTRunner.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }

        for (int i = 0; i < n; i++)
            ZD[i] = minZ * DMT[i];

        ArrayHelper.SumVectors(ZD, B, E);

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba1.txt", "E", E);

    }
}
