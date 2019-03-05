package Common;

public class ArrayHelper {
    public static void PlusMatrix(double[][] M1, double[][] M2, double[][] MResult) {
        for (int i = 0; i < M1[0].length; i++)
            for (int j = 0; j < M1[0].length; j++)
                MResult[i][j] = M1[i][j] + M2[i][j];
    }

    public static void MinusMatrix(double[][] M1, double[][] M2, double[][] MResult) {
        for (int i = 0; i < M1[0].length; i++)
            for (int j = 0; j < M1[0].length; j++)
                MResult[i][j] = M1[i][j] - M2[i][j];
    }

    public static void MatrixScalar(double[][] M1, double a, double[][] MResult) {
        for (int i = 0; i < M1[0].length; i++)
            for (int j = 0; j < M1[0].length; j++)
                MResult[i][j] = M1[i][j] * a;
    }

    public static double MinVector(double[] V) {
        double min = V[0];

        for (int i = 1; i < V.length; i++) {
            if (min > V[i])
                min = V[i];
        }

        return min;
    }

    public static double MaxVector(double[] V) {
        double max = V[0];

        for (int i = 1; i < V.length; i++) {
            if (max < V[i])
                max = V[i];
        }

        return max;
    }

    public static void SumVectors(double[] V1, double[] V2, double[] VResult) {
        for (int i = 0; i < V1.length; i++)
            VResult[i] = V1[i] + V2[i];
    }
}
