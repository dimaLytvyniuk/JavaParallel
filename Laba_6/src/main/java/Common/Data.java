package Common;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Data {
    private static Random random = new Random();

    public static void generateVector(double[] vector) {
        for (int i = 0; i < vector.length; i++)
            vector[i] = 1;
    }

    public static void generateMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            generateVector(matrix[i]);
    }
}
