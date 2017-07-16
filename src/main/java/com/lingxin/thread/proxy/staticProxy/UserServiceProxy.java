package com.lingxin.thread.proxy.staticProxy;

import com.lingxin.thread.proxy.dynamicProxy.UserService;
import com.lingxin.thread.proxy.dynamicProxy.UserServiceImpl;

/**
 * Created by Mr_Li on 2017/7/16.
 */
public class UserServiceProxy implements UserService {
    //代理类持有一个委托类的对象引用
    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String getName(int id) {
        return userService.getName(id);
    }

    @Override
    public Integer getAge(int id) {
        return userService.getAge(id);
    }
}
