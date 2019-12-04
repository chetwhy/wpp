package cn.yccoding.wpp.common.algs;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectionSort {

    public static void sort(Comparable[] a) {
        int arrSize = a.length;
        for (int i = 0; i < arrSize; i++) {
            int minIndex = i;   // 假设最小值索引
            for (int j = i + 1; j < arrSize; j++) {
                if (less(a[j], a[minIndex])) {
                    minIndex = j;
                }
            }
            exch(a, i, minIndex);
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
