package com.lingxin.thread.procus;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Customer implements Runnable{

    private volatile boolean isRunning =true;
    //private SynchronousQueue<ProData> queue;
    BlockingQueue<ProData> queue;

//    public Customer(SynchronousQueue<ProData> queue) {
//        this.queue = queue;
//    }


    public Customer(BlockingQueue<ProData> queue) {
        this.queue = queue;
    }

    public void stop(){
        isRunning =false;
    }

    @Override
    public void run(){

        Random r=new Random();

        System.out.println("当前消费者的姓名为:"+Thread.currentThread().getName());

        try{
            while(true){
                ProData data =queue.take();
                if (null != data) {
                    int result = data.getData()*data.getData();
                    System.out.println(Thread.currentThread().getName()+":"+MessageFormat.format("{0}*{1}={2}",data.getData(),data.getData(),result));
                }else{
                    System.out.println(Thread.currentThread().getName()+":毛都没有....");
                }
            }
        }catch (InterruptedException e){
            System.out.println("没补货到吗?...");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
