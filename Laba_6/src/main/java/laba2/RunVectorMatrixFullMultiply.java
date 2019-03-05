package laba2;

import java.util.concurrent.Semaphore;

public class RunVectorMatrixFullMultiply implements Runnable {
    private double[][] M;
    private double[] V;
    private double[] VResult;

    Thread t;

    public RunVectorMatrixFullMultiply(double[][] M, double[] V, double[] VResult) {
        this.M = M;
        this.V = V;
        this.VResult = VResult;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Semaphore firstSemaphore = new Semaphore(1);
        Semaphore secondSemaphore = new Semaphore(1);

        RunVectorsMatrixHalfMultiply firstHalfMultiply = new RunVectorsMatrixHalfMultiply(M, V, VResult, true, firstSemaphore, secondSemaphore);
        RunVectorsMatrixHalfMultiply secondHalfMultiply = new RunVectorsMatrixHalfMultiply(M, V, VResult, false, secondSemaphore, firstSemaphore);

        try {
            firstHalfMultiply.t.join();
            secondHalfMultiply.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }
    }
}
