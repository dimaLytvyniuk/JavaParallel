package laba6;

import Common.ArrayHelper;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RunMatrixMultiply extends RecursiveTask<double[][]> {
    private double[][] M1;
    private double[][] M2;
    int n, start, end;

    public RunMatrixMultiply(
        double[][] M1,
        double[][] M2,
        int start,
        int end) {
            this.n = M1[0].length;
            this.M1 = M1;
            this.M2 = M2;
            this.start = start;
            this.end = end;
    }

    @Override
    protected double[][] compute() {
        int length = end - start;
        if (length <= 2)
            return multiply();

        int k = length % 2 == 0 ? length / 2 : (length / 2 + 1);
        k += start;
        RunMatrixMultiply firstTask = new RunMatrixMultiply(M1, M2, start, k);
        RunMatrixMultiply secondTask = new RunMatrixMultiply(M1, M2, k, end);
        firstTask.fork();
        secondTask.fork();

        double[][] result = new double[n][n];
        double[][] firstPart = firstTask.join();
        double[][] secondPart = secondTask.join();

        ArrayHelper.PlusMatrix(firstPart, secondPart, result);
        return result;
    }

    private double[][] multiply() {
        double[][] MResult = new double[n][n];

        for (int i = start; i < end; i++)
        {
            for (int j = 0; j < n; j++)
            {
                MResult[i][j] = 0;
                for (int r = 0; r < n; r++)
                {
                    MResult[i][j] += M1[i][r] * M2[r][j];
                }
            }
        }

        return MResult;
    }

    public static double[][] startForkJoinMultiply(double[][] MM1, double[][]MM2) {
        int n = MM1[0].length;
        ForkJoinTask<double[][]> task = new RunMatrixMultiply(MM1, MM2, 0, n);
        return new ForkJoinPool().invoke(task);
    }
}
