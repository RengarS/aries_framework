package com.aries.aries_boot.bean;

/**
 * 返回数据对象
 * Created by Aries on 2017/7/23.
 */
public class Data {
    /**
     * 模型数据
     */

    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel(){
        return model;
    }
}
