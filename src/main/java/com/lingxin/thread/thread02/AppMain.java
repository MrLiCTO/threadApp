package com.lingxin.thread.thread02;

import java.util.concurrent.*;


/**
 * Created by Administrator on 2016/11/24.
 */
public class AppMain {
    public static void main(String[] args) throws Exception{
        //SynchronousQueue queue=new SynchronousQueue();
        BlockingQueue<ProData> queue =new LinkedBlockingDeque<>();

        Producer p1=new Producer(queue);
        Producer p2=new Producer(queue);
        Producer p3=new Producer(queue);

        Customer c1=new Customer(queue);
        Customer c2=new Customer(queue);
        Customer c3=new Customer(queue);

        ExecutorService threadPool = Executors.newCachedThreadPool();

        threadPool.submit(p1);
        threadPool.submit(p2);
        threadPool.submit(p3);
        threadPool.submit(c1);
        threadPool.submit(c2);
        threadPool.submit(c3);
        Thread.sleep(5000);


        p1.stop();
        p2.stop();
        p3.stop();

        Thread.sleep(5000);

        threadPool.shutdownNow();

        System.out.println("已经shutDown...");
    }
}
