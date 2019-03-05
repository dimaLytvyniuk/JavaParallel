package laba2;

import java.util.concurrent.Semaphore;

public class RunFindingA implements Runnable {
    private double[] A, B, D, E;
    private double[][] MC, MZ, MM;
    private int n;
    private Semaphore writeSemaphore;

    Thread t;

    public RunFindingA(
            double[] A,
            double[] B,
            double[] D,
            double[] E,
            double[][] MC,
            double[][] MZ,
            double[][] MM,
            Semaphore writeSemaphore)
    {
        this.A = A;
        this.B = B;
        this.D = D;
        this.E = E.clone();
        this.MC = MC;
        this.MZ = MZ;
        this.MM = MM;
        this.writeSemaphore = writeSemaphore;
        n = A.length;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double[] BMC = new double[n];
        double[] DMZ = new double[n];
        double[] EMM = new double[n];

        RunVectorMatrixFullMultiply BMCRun = new RunVectorMatrixFullMultiply(MC, B, BMC);
        RunVectorMatrixFullMultiply DMZRun = new RunVectorMatrixFullMultiply(MZ, D, DMZ);
        RunVectorMatrixFullMultiply EMMRun = new RunVectorMatrixFullMultiply(MM, E, EMM);

        try {
            BMCRun.t.join();
            DMZRun.t.join();
            EMMRun.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }

        for (int i = 0; i < n; i++) {
            A[i] = BMC[i] + DMZ[i] + EMM[i];
        }

        InputOutput inputOutput = new InputOutput();
        inputOutput.OutputToFileVector("laba2.txt", "A", A, writeSemaphore);
    }
}
