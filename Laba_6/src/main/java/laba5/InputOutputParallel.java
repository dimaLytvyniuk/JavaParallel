package laba5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;

class InputOutputParallel implements Runnable {
    private BlockingQueue queue;

    public InputOutputParallel(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            try {
                String[] data = queue.take().toString().split("/");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("laba5.txt", true))) {
                    for (int j = 0; j < data.length; j++) {
                        writer.write(data[j]);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static String CreateOtputStringForVector(String vectorName, double[] vector) {
        String result = vectorName + ":/";

        for (int i = 0; i < vector.length; i++)
            result += vector[i] + " ";

        result += "/";
        return result;
    }

    public static String CreateOtputStringForMatrix(String matrixName, double[][] matrix) {
        String result = matrixName + ":/";
        int n = matrix[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                result += matrix[i][j] + " ";

            result += "/";
        }

        return result;
    }
}
