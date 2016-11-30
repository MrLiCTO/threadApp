package com.lingxin.thread.procus;

/**
 * Created by Administrator on 2016/11/24.
 */
public class ProData {
    private final int data;

    public ProData(int data) {
        this.data = data;
    }

    public ProData(String strData) {
        this.data = Integer.parseInt(strData);
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ProData{" +
                "data=" + data +
                '}';
    }
}
