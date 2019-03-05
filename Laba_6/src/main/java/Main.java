import laba1.Laba1;
import laba2.Laba2;
import laba3.Laba3;
import laba4.Laba4;
import laba5.Laba5;
import laba6.Laba6;

public class Main {
    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        Laba1.run();
        long finish = System.currentTimeMillis();

        System.out.println(finish - start);
    }
}
