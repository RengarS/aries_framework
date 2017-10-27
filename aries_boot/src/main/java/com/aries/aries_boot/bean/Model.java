package com.aries.aries_boot.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aries on 2017/9/10.
 */
public class Model {

    Map<String, Object> map = new HashMap<>();

    public void addAttribute(String key, Object value) {
        map.put(key, value);
    }

    public Map getModelMap() {
        return map;
    }
}
