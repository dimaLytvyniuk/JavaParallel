package laba2;

import java.util.concurrent.Semaphore;

public class RunVectorsMatrixHalfMultiply implements Runnable {
    private double[][] M;
    private double[] V;
    double[] VResult;
    boolean startWithFirst;
    Thread t;
    int n;

    public RunVectorsMatrixHalfMultiply(
            double[][] M,
            double[] V,
            double[] VResult,
            boolean startWithFirst,
            Semaphore semaphoreRelease,
            Semaphore semaphoreWait) {
        t = new Thread(this);
        this.n = V.length;
        this.M = M;
        this.V = V;
        this.VResult = VResult;
        this.startWithFirst = startWithFirst;

        t.start();
    }

    @Override
    public void run() {
        if (startWithFirst)
            multipleFirstHalfStart();
        else
            multipleSecondHalfStart();
    }

    private void multipleFirstHalfStart() {
        int k = n % 2 == 0 ? n / 2 : (n / 2 + 1);
        for (int i = 0; i < k; i++)
        {
            VResult[i] = 0;
            for (int j = 0; j < n; j++)
            {
                    VResult[i] += V[j] * M[i][j];
            }
        }
    }

    private void multipleSecondHalfStart() {
        int k = n % 2 == 0 ? n / 2 : (n / 2 + 1);
        for (int i = k; i < n; i++)
        {
            VResult[i] = 0;
            for (int j = 0; j < n; j++)
            {
                VResult[i] += V[j] * M[i][j];
            }
        }
    }
}
