package com.lingxin.thread.thread03;

/**
 * Created by Administrator on 2016/11/24.
 */
public class PCData {
    private long data;

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "data=" + data +
                '}';
    }
}
