package com.aries.aries_boot.bean;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/6/3.
 */
public class Handler {
    /**
     * controller类
     */
    private Class<?> controllerClass;
    /**
     * action方法
     */
    private Method actionMethod;
    public Handler(Class<?> controllerClass,Method actionMethod){
        this.controllerClass=controllerClass;
        this.actionMethod=actionMethod;
    }

    public Class<?> getControllerClass(){
        return controllerClass;
    }
    public Method getActionMethod(){
        return actionMethod;
    }
}
