package cn.yccoding.wpp.common.algs;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {

    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        Arrays.stream(a).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] arr1 = {1, 1, 1, 1, 1};
        Integer[] arr2 = {7, 4, 3, 1, 2};

        show(arr2);
        sort(arr2);
        show(arr2);
    }
}
