package com.lingxin.thread.future;

/**
 * Created by Administrator on 2016/11/24.
 */
public class AppMainf {
    public static void main(String[] args) {
        Clientf clientf=new Clientf();
        Data data = clientf.request("name");
        System.out.println("请求结束!");
        try {
            Thread.sleep(3000);
            System.out.println("响应数据为:"+data.getResult());
        } catch (InterruptedException e) {
        }
    }
}
