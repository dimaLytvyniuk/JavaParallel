package laba6;

public class RunFindingA implements Runnable {
    private double[] A, B, D, E;
    private double[][] MC, MZ, MM;
    private int n;

    Thread t;

    public RunFindingA(
            double[] A,
            double[] B,
            double[] D,
            double[] E,
            double[][] MC,
            double[][] MZ,
            double[][] MM)
    {
        this.A = A;
        this.B = B;
        this.D = D;
        this.E = E.clone();
        this.MC = MC;
        this.MZ = MZ;
        this.MM = MM;
        n = A.length;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double[] BMC = RunMatrixVectorMultiply.startForkJoinMultiply(MC, B);
        double[] DMZ = RunMatrixVectorMultiply.startForkJoinMultiply(MZ, D);
        double[] EMM = RunMatrixVectorMultiply.startForkJoinMultiply(MM, E);

        for (int i = 0; i < n; i++) {
            A[i] = BMC[i] + DMZ[i] + EMM[i];
        }

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba6.txt", "A", A);
    }
}
