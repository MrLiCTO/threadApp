package com.lingxin.thread.str;

/**
 * Created by Mr_Li on 2017/1/7.
 */
public class TestStr {
    public static void main(String[] args) {
        String str1=new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1==str1.intern());
        String str2=new StringBuilder("ja").append("va").toString();
        System.out.print(str2==str2.intern());
    }
}
