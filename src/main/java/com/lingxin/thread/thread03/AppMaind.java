package com.lingxin.thread.thread03;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/11/24.
 */
public class AppMaind {

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        PCDataFactory pcDataFactory=new PCDataFactory();
        int bufferSize=1024;
        Disruptor<PCData> disruptor=new Disruptor<PCData>(pcDataFactory,bufferSize,service, ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new Customerd(),new Customerd(),new Customerd());
        disruptor.start();
        RingBuffer<PCData> ringBuffer=disruptor.getRingBuffer();
        Producerd producerd=new Producerd(ringBuffer);
        ByteBuffer byteBuffer=ByteBuffer.allocate(8);
        for(long l=0;true;l++){
            byteBuffer.putLong(0,l);
            producerd.pushData(byteBuffer);
            Thread.sleep(1000);
            System.out.println("add data "+l);
        }
    }
}
