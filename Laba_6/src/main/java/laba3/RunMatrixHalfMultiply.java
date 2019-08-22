package laba3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class RunMatrixHalfMultiply implements Runnable {
    private double[][] M1;
    private double[][] M2;
    double[][] MResult;
    boolean startWithFirst;
    int n;
    Semaphore semaphoreRelease;
    Semaphore semaphoreWait;

    public RunMatrixHalfMultiply(
        double[][] M1,
        double[][] M2,
        double[][] MResult,
        boolean startWithFirst,
        Semaphore semaphoreRelease,
        Semaphore semaphoreWait) {
            this.n = M1[0].length;
            this.M1 = M1;
            this.M2 = M2;
            this.MResult = MResult;
            this.startWithFirst = startWithFirst;
            this.semaphoreRelease = semaphoreRelease;
            this.semaphoreWait = semaphoreWait;
    }

    @Override
    public void run() {
        if (startWithFirst)
            multipleFirstHalfStart();
        else
            multipleSecondHalfStart();
    }

    private void multipleFirstHalfStart() {
        try {
            semaphoreRelease.acquire();
            int k = n % 2 == 0 ? n / 2 : (n / 2 + 1);
            for (int i = 0; i < k; i++)
            {
                for (int j = 0; j < k; j++)
                {
                    MResult[i][j] = 0;
                    double c = 0;
                    double y = 0;
                    double p = 0;
                    for (int r = 0; r < n; r++)
                    {
                        y = (M1[i][r] * M2[r][j]) - c ;
                        p = MResult[i][j] + y;
                        c = (p - MResult[i][j]) - y;
                        MResult[i][j] = p;
                    }
                }
            }

            semaphoreRelease.release();
            semaphoreWait.acquire();
            for (int i = 0; i < k; i++)
            {
                for (int j = k; j < n; j++)
                {
                    MResult[i][j] = 0;
                    double c = 0;
                    double y = 0;
                    double p = 0;
                    for (int r = 0; r < n; r++)
                    {
                        y = (M1[i][r] * M2[r][j]) - c ;
                        p = MResult[i][j] + y;
                        c = (p - MResult[i][j]) - y;
                        MResult[i][j] = p;
                    }
                }
            }
            semaphoreWait.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void multipleSecondHalfStart() {
        try {
            semaphoreRelease.acquire();
            int k = n % 2 == 0 ? n / 2 : (n / 2 + 1);
            for (int i = k; i < n; i++)
            {
                for (int j = k; j < n; j++)
                {
                    MResult[i][j] = 0;
                    double c = 0;
                    double y = 0;
                    double p = 0;
                    for (int r = 0; r < n; r++)
                    {
                        y = (M1[i][r] * M2[r][j]) - c ;
                        p = MResult[i][j] + y;
                        c = (p - MResult[i][j]) - y;
                        MResult[i][j] = p;
                    }
                }
            }

            semaphoreRelease.release();
            semaphoreWait.acquire();
            for (int i = k; i < n; i++)
            {
                for (int j = 0; j < k; j++)
                {
                    MResult[i][j] = 0;
                    double c = 0;
                    double y = 0;
                    double p = 0;
                    for (int r = 0; r < n; r++)
                    {
                        y = (M1[i][r] * M2[r][j]) - c ;
                        p = MResult[i][j] + y;
                        c = (p - MResult[i][j]) - y;
                        MResult[i][j] = p;
                    }
                }
            }
            semaphoreWait.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
