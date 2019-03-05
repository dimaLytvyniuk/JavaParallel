package laba6;

import Common.ArrayHelper;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RunMatrixVectorMultiply extends RecursiveTask<double[]> {
    private double[][] M;
    private double[] V;
    int n, start, end;

    public RunMatrixVectorMultiply(double[][] M, double[] V, int start, int end) {
        this.n = V.length;
        this.M = M;
        this.V = V;
        this.start = start;
        this.end = end;
    }

    @Override
    protected double[] compute() {
        int length = end - start;
        if (length <= 2)
            return multiply();

        int k = length % 2 == 0 ? length / 2 : (length / 2 + 1);
        k += start;
        RunMatrixVectorMultiply firstTask = new RunMatrixVectorMultiply(M, V, start, k);
        RunMatrixVectorMultiply secondTask = new RunMatrixVectorMultiply(M, V, k, end);
        firstTask.fork();
        secondTask.fork();

        double[] result = new double[n];
        double[] firstPart = firstTask.join();
        double[] secondPart = secondTask.join();

        ArrayHelper.SumVectors(firstPart, secondPart, result);
        return result;
    }

    private double[] multiply() {
        double[] VResult = new double[n];
        for (int i = start; i < end; i++)
        {
            VResult[i] = 0;
            for (int j = 0; j < n; j++)
            {
                VResult[i] += V[j] * M[i][j];
            }
        }

        return VResult;
    }

    public static double[] startForkJoinMultiply(double[][] MM, double[] VV) {
        int n = VV.length;
        ForkJoinTask<double[]> task = new RunMatrixVectorMultiply(MM, VV, 0, n);
        return new ForkJoinPool().invoke(task);
    }
}
