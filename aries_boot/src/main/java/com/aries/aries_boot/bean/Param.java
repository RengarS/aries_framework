package com.aries.aries_boot.bean;



import com.aries.aries_boot.util.CastUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/4.
 * 请求参数对象
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap){
        this.paramMap=paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     * @param name
     * @return
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }
    /**
     * 获取所有字段信息
     */
    public Map<String,Object> getParamMap   (){
        return paramMap;
    }


}
