package com.lingxin.thread.thread04;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Clientf {
    public Data request(final String queryStr){
        final FutureData futureData=new FutureData();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                       RealData realData=new RealData(queryStr);
                       futureData.setRealData(realData);
                    }
                }
        ).start();
        return futureData;
    }
}
