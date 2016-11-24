package com.lingxin.thread.thread04;

/**
 * Created by Administrator on 2016/11/24.
 */
public class RealData implements Data {
    protected final String result;

    public RealData(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(str);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        this.result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
