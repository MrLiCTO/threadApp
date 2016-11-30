package com.lingxin.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator on 2016/11/24.
 */
public class PCDataFactory implements EventFactory<PCData>{
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
