package com.lingxin.thread.disruptor;

import com.lmax.disruptor.WorkHandler;

import java.text.MessageFormat;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Customerd implements WorkHandler<PCData>{
    @Override
    public void onEvent(PCData pcData) throws Exception {
        long result=pcData.getData()*pcData.getData();
        System.out.println("线程名称为"+Thread.currentThread().getName()+":"+ MessageFormat.format("{0}*{1}={2}",pcData.getData(),pcData.getData(),result));
    }
}
