package com.lingxin.thread.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Producerd{
    private final RingBuffer<PCData> ringBuffer;

    public Producerd(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer bb){
        long sequense=ringBuffer.next();
        try{
            PCData pcData = ringBuffer.get(sequense);
            pcData.setData(bb.getLong(0));
        }finally {
            ringBuffer.publish(sequense);
        }
    }
}
