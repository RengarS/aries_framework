package com.aries.discovery.loadbalance;

import com.aries.discovery.core.ServiceHelper;

import java.util.List;

public class LoadBalanceHelper {

    /**
     * 负载均衡
     *
     * @param serviceName
     * @return
     */
    public static String getServiceUrl(String serviceName) {
        List<String> serviceUrls = ServiceHelper.getIPListByServiceName(serviceName);
        if (serviceUrls.size() == 1) {
            return
                    serviceUrls.get(0);
        }

        return
                serviceUrls.get(getRandomsNum(serviceUrls.size()));
    }

    /**
     * 产生随机数
     *
     * @param length
     * @return
     */
    private static int getRandomsNum(int length) {
        int x = (int) (Math.random() * length);
        return x;
    }

}
