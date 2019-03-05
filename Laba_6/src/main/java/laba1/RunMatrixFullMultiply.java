package laba1;

import java.util.concurrent.Semaphore;

public class RunMatrixFullMultiply implements Runnable {
    private double[][] M1;
    private double[][] M2;
    private double[][] MResult;

    Thread t;

    public RunMatrixFullMultiply(double[][] M1, double[][] M2, double[][] MResult) {
        this.M1 = M1;
        this.M2 = M2;
        this.MResult = MResult;

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Semaphore firstSemaphore = new Semaphore(1);
        Semaphore secondSemaphore = new Semaphore(1);

        RunMatrixHalfMultiply firstHalfMultiply = new RunMatrixHalfMultiply(M1, M2, MResult, true, firstSemaphore, secondSemaphore);
        RunMatrixHalfMultiply secondHalfMultiply = new RunMatrixHalfMultiply(M1, M2, MResult, false, secondSemaphore, firstSemaphore);

        try {
            firstHalfMultiply.t.join();
            secondHalfMultiply.t.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interupted");
        }
    }
}
