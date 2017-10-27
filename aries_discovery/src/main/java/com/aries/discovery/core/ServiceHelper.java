package com.aries.discovery.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by aries on 2017-9-20
 * <p>
 * service 的name和ip集合的映射
 */
public class ServiceHelper {

    //key:service的name。value:service的name对应的ip集合
    private static Map<String, List<String>> SERVICE_IPS_MAP = new ConcurrentHashMap<>();

    /**
     * 服务注册
     *
     * @param serviceName 注册的service的name
     * @param serviceIP   注册的service的ip
     */
    public void addServiceIntoMap(String serviceName, String serviceIP) {
        if (null == SERVICE_IPS_MAP.get(serviceName)) {
            List<String> ipList = new ArrayList<>();
            ipList.add(serviceIP);
            SERVICE_IPS_MAP.put(serviceName, ipList);
        } else {
            List<String> ipList = SERVICE_IPS_MAP.get(serviceName);
            ipList.add(serviceIP);
            SERVICE_IPS_MAP.put(serviceName, ipList);
        }
    }

    /**
     * 服务从map中移除
     *
     * @param serviceName
     * @param serviceIP
     */
    public static void removeServiceFromMap(String serviceName, String serviceIP) {
        List<String> ipList = SERVICE_IPS_MAP.get(serviceName);
        Iterator<String> ipIterator = ipList.iterator();
        while (ipIterator.hasNext()) {
            if (ipIterator.next() == serviceIP) {
                ipIterator.remove();
            }
        }
    }

    /**
     * 根据Service name返回ip集合
     *
     * @param serviceName
     * @return List<String>
     */
    public static List<String> getIPListByServiceName(String serviceName) {
        return
                SERVICE_IPS_MAP.get(serviceName);
    }


    public static Map<String, List<String>> getServiceIpsMap() {
        return SERVICE_IPS_MAP;
    }

    public static void setServiceIpsMap(Map<String, List<String>> serviceIpsMap) {
        SERVICE_IPS_MAP = serviceIpsMap;
    }
}
