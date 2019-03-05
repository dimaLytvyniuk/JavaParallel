package laba5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class InputOutput {
    public void OutputToFileMatrix(String fileName, String MatrixName, double[][] matrix) {
        int n = matrix[0].length;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(MatrixName + ":");
            writer.newLine();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                    writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OutputToFileVector(String fileName, String VectorName, double[] vector) {
        int n = vector.length;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(VectorName + ":");
            writer.newLine();

            for (int i = 0; i < n; i++)
                writer.write(vector[i] + " ");

            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
