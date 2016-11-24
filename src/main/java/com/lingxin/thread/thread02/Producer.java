package com.lingxin.thread.thread02;


import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Producer implements Runnable{

    private volatile boolean isRunning =true;
    //private SynchronousQueue<ProData> queue;
    BlockingQueue<ProData> queue;
    private static AtomicInteger count=new AtomicInteger();
    private final static int SLEEP_TIME =1000;

    //public Producer(SynchronousQueue<ProData> queue) {
    //    this.queue = queue;
    //}


    public Producer(BlockingQueue<ProData> queue) {
        this.queue = queue;
    }

    public void stop(){
        isRunning =false;
    }

    @Override
    public void run(){

        ProData data=null;
        Random r=new Random();

        System.out.println("当前生产者的姓名为:"+Thread.currentThread().getName());

        try {
            while (isRunning){
                Thread.sleep(r.nextInt(SLEEP_TIME));
                data=new ProData(count.addAndGet(1));
                System.out.println(Thread.currentThread().getName()+":"+data + "被放进了queue中");
                if (!queue.offer(data)){
                    System.out.println(Thread.currentThread().getName()+":"+data + "没放进去..出错了...");
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
