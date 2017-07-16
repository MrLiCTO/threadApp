package com.lingxin.thread.proxy.staticProxy;

import com.lingxin.thread.proxy.dynamicProxy.CglibProxy;
import com.lingxin.thread.proxy.dynamicProxy.MyInvocationHandler;
import com.lingxin.thread.proxy.dynamicProxy.UserService;
import com.lingxin.thread.proxy.dynamicProxy.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MainApp {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
        System.out.println(userServiceProxy.getName(1));
        System.out.println(userServiceProxy.getAge(1));
    }
}