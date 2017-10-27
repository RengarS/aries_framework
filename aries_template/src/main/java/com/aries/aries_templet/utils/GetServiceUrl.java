package com.aries.aries_templet.utils;

import com.aries.aries_templet.core.RestTemplate;

public class GetServiceUrl {

    /**
     * 根据discovery服务器的地址，和service的name获取service地址
     *
     * @param disUrl
     * @param serviceName
     * @return
     */
    public static String getUrlByDisUrlAndName(String disUrl, String serviceName) {
        return
                RestTemplate.postByServiceUrlAndGetString(disUrl, serviceName);
    }
}
