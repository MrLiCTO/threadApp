package com.lingxin.thread.thread05;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/11/25.
 */
public class OddEvenSort {

    public static void main(String[] args) {
        int[] arr1={
                2,3,4,345,435,46,3,346,56,345,5678,34,678,344,234,678,789,34,512,78,945,612,568,89,023,68,879,2345,42,6789,890,34,512,36789,12,3
        };
        try {
            OddEvenSortTask.oddEvenSort(arr1);
            Arrays.stream(arr1).forEach(System.out::print);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static int exchFlag=1;


    public synchronized static void setExchFlag(int exchFlag) {
        exchFlag = exchFlag;
    }

    public synchronized static int getExchFlag() {
        return exchFlag;
    }

    public static class OddEvenSortTask implements Runnable{

        int i;

        CountDownLatch latch;

        static int[] arr;

        public OddEvenSortTask(int i, CountDownLatch latch) {
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            if(arr[i]>arr[i+1]){
                int tem =arr[i];
                arr[i]=arr[i+1];
                arr[i+1]=tem;
                setExchFlag(1);
            }
            latch.countDown();
        }

        public static void oddEvenSort(int arry[]) throws InterruptedException{
            int start=0;
            arr=arry;
            ExecutorService pool = Executors.newCachedThreadPool();
            while(getExchFlag()==1||start==1){
                setExchFlag(0);
                CountDownLatch countDownLatch=new CountDownLatch(arr.length%2==0?start:0);
                for (int i = start; i < arr.length-1; i+=2) {
                    pool.submit(new OddEvenSortTask(i,countDownLatch));
                }
                countDownLatch.countDown();
                if(start==1){
                    start=0;
                }else{
                    start=1;
                }
            }
        }
    }
}
