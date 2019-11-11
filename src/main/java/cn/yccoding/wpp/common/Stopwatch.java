package cn.yccoding.wpp.common;

/**
 * @author : Chet
 * @description : 计时
 * @date : 2019/11/11
 */
public class Stopwatch {
    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
}
