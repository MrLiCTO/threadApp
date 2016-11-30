package com.lingxin.thread.fun;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/10/11.
 */
public class TestThread01 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<13;i++){
            final int index=i;
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            executorService.execute(/*new Runnable() {
                final String x="我很好"+Math.random()*10+index;
                public void run() {
                    System.out.println(index+x);
                }
            }*/
                    ()->System.out.println(index)
            );
        }

    }

}
