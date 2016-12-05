package com.lingxin.thread.nioEncho;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/12/5.
 */
public class EnchoClient {
    private LinkedList<ByteBuffer> outq;

    public EnchoClient() {
        this.outq = new LinkedList<ByteBuffer>();
    }

    public LinkedList<ByteBuffer> getOutq() {
        return outq;
    }

    public void enqueue(ByteBuffer bb) {
        outq.add(bb);
    }


}
