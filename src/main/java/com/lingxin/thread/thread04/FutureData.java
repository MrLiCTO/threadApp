package com.lingxin.thread.thread04;

/**
 * Created by Administrator on 2016/11/24.
 */
public class FutureData implements Data{
    protected RealData realData=null;
    protected boolean isReady= false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady=true;
        notifyAll();
    }

    @Override
    public synchronized String getResult(){
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return realData.getResult();
    }
}
