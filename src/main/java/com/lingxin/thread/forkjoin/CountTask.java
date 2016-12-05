package com.lingxin.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2016/11/30.
 */
public class CountTask extends RecursiveTask<Integer>{

    public static void main(String[] args) throws Exception{
        ForkJoinPool pool=new ForkJoinPool();
        CountTask task=new CountTask(1,1000000);
        long start = System.currentTimeMillis();
        ForkJoinTask<Integer> result = pool.submit(task);
        int res=result.get();
        long end = System.currentTimeMillis();
        System.out.println("结果为:"+res+"耗时:"+(end-start)+"ms");
    }

    private static final int ThreadHold =2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }



    @Override
    protected Integer compute() {
        int sum=0;
        boolean canCompute=(end - start)<ThreadHold;
        if (canCompute){
            for (int i = start; i <=end ; i++) {
                sum+=i;
            }
        }else {
            int middle=(end+start)/2;
            CountTask taskR = new CountTask(start, middle);
            CountTask taskL = new CountTask(middle+1, end);
            taskR.fork();
            taskL.fork();
            Integer resultL = taskL.join();
            Integer resultR = taskR.join();
            sum=resultL+resultR;
        }
        return sum;
    }
}
