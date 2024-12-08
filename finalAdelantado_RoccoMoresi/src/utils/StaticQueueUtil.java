package utils;

import tdas.StaticQueue;

public class StaticQueueUtil {

    //EJERCICIO 1 B (COMPLEJIDAD O(n * log n))

    public static boolean isFibonacci(int n) {
        int a = 0, b = 1;
        if (n == a || n == b) {
            return true;
        }
        int c = a + b;
        while (c <= n) {
            if (c == n) {
                return true;
            }
            a = b;
            b = c;
            c = a + b;
        }
        return false;
    }

    public static void markFibonacci(StaticQueue queue) {
        for (int i = 1; i <= 100; i++) {
            queue.add(i);
        }

        for (int i = 1; i <= 100; i++) {
            if (isFibonacci(i)) {
                queue.mark(i);
            }
        }
    }

    public static void showMarkedElements(StaticQueue queue) {
        int[] elements = queue.toArray();
        System.out.println("Elementos marcados (Fibonacci):");
        for (int element : elements) {
            if (queue.isMarked(element)) {
                System.out.println(element);
            }
        }
    }
}
