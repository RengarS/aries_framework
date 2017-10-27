package com.aries.aries_boot.resolvers;

import javax.servlet.http.HttpServletRequest;

/**
 * create by aries
 * Spring的model.addAttribute()的实现
 */
public class Model {
    private HttpServletRequest request;

    public Model(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 向页面中添加Bean
     *
     * @param key
     * @param value
     */
    public void addAttribute(String key, Object value) {
        request.setAttribute(key, value);
        System.out.println("addAttribute:" + key + "  " + value.toString());
    }
}
