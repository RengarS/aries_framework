package com.aries.discovery.util;

import com.aries.discovery.core.ServiceHelper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * create by aries on 2017-9-20
 * <p>
 * 检测每个service是否在线
 */
public class ServiceCheck {

    private static final OkHttpClient client = new OkHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(ServiceCheck.class);

    private static Map<String, List<String>> SERVICE_MAP = ServiceHelper.getServiceIpsMap();

    /**
     * 检测service是否在线。
     * 如果service离线，从map中移除。else  do nothing
     */
    public void checkService() {
//        for (Map.Entry<String, List<String>> entry : SERVICE_MAP.entrySet()) {
//            String serviceName = entry.getKey();
//            List<String> ipList = entry.getValue();
//            for (String ip : ipList) {
//                try {
//                    int code = RestTemplate.checkServiceServer(ip);
//                    if (code != 200) {
//                        ServiceHelper.removeServiceFromMap(serviceName, ip);
//                    }
//                } catch (Exception e) {
//                    logger.error(serviceName + ":" + ip + "服务离线了");
//                }
//            }
//        }
//
//    }
//
//    private static int checkServiceServer(String ip) throws Exception {
//        String serviceUrl = ip + "/discovery/check";
//        Request request = new Request.Builder()
//                .url(serviceUrl)
//                .build();
//        Call call = client.newCall(request);
//        Response response = call.execute();
//        return response.code();
    }

}
