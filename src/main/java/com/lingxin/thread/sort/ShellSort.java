package com.lingxin.thread.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.IntConsumer;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ShellSort implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        //int[] arr1 = {
        Integer[] arr1 = {
                2, 3, 4, 345, 435, 46, 3, 346, 56, 345, 5678, 34, 678, 344, 234, 678, 789, 34, 512, 78, 945, 612, 568, 89, 23, 68, 879, 2345, 42, 6789, 890, 34, 512, 36789, 12, 3
        };
        ArrayList<Integer> list = new ArrayList<>();
        Function<Integer[], List<Integer>> fun = arr -> {
            for (Integer a : arr) {
                if (a % 2 == 0)
                    list.add(a);
            }
            return list;
        };
        Function<Integer[], List<Integer>> fun1 = arr->{Arrays.stream(arr).mapToInt(x->x*x).forEach(x->list.add(x)); return list;};
        //shellSort(arr1);
        Arrays.stream(fun1.apply(arr1).toArray()).forEach(System.out::println);
    }

    int i = 0;
    int h = 0;
    CountDownLatch latch = null;
    static int[] arry = null;

    public ShellSort(int i, int h, CountDownLatch latch) {
        this.i = i;
        this.h = h;
        this.latch = latch;
    }

    @Override
    public void run() {
        if (arry[i] < arry[i - h]) {
            int tem = arry[i];
            int j = i - h;
            while (j >= 0 && arry[j] > tem) {
                arry[j + h] = arry[j];
                j -= h;
            }
            arry[j + h] = tem;
            System.out.println("arry[" + i + "]=" + arry[i] + "被放到了" + (j + h) + "位置");
        }
        latch.countDown();
    }

    public static void shellSort(int[] arr) {
        arry = arr;
        int h = 1;
        CountDownLatch l = null;
        ExecutorService pool = Executors.newCachedThreadPool();
        while (h <= arry.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            if (h >= 4) {
                System.out.println("数组长度为:" + arry.length + "当前间隔h=" + h);
            } else {
                System.out.println("插入排序阶段:当前间隔h=" + h);
            }
            if (h >= 4) {
                l = new CountDownLatch(arry.length - h);
            }
            for (int i = h; i < arry.length; i++) {
                if (h >= 4) {
                    pool.execute(new ShellSort(i, h, l));
                } else {
                    if (arry[i] < arry[i - h]) {
                        int tem = arry[i];
                        int j = i - h;
                        while (j >= 0 && arry[j] > tem) {
                            arry[j + h] = arry[j];
                            j -= h;
                            System.out.println(arry[i] + "与" + arry[j] + "发生了交换");
                        }
                        arry[j + h] = tem;
                    }
                }
            }
            if (h >= 4) {
                try {
                    l.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            h = (h - 1) / 3;
        }
    }
}
