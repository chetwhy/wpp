package cn.yccoding.wpp.common.algs;

import java.util.Arrays;

public class BaseSort {

    public static void sort(Comparable[] a) {

    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        if (i != j) {
            Comparable t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }

    private static void show(Comparable[] a) {
        Arrays.stream(a).forEach(i-> System.out.print(i+ " "));
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }
}
