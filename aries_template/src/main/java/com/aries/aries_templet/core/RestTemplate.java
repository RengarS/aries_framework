package com.aries.aries_templet.core;

import com.aries.aries_templet.utils.GetServiceUrl;
import com.aries.aries_templet.utils.JsonToObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RestTemplate {

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(RestTemplate.class);

    /**
     * 发送post请求，获取返回的json体并转成Object返回
     *
     * @param serviceName
     * @param requestJsonBody
     * @return
     */
    public static <T> T postByNameAndGetObject(String serviceName, String requestJsonBody, Class<T> responseJsonObject) {
        //TODO
        String serviceUrl = GetServiceUrl.getUrlByDisUrlAndName("", serviceName);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJsonBody);
        Request request = new Request.Builder().url(serviceUrl).post(body).build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            String responseJson = new String(response.body().bytes(), "UTF-8");
            return
                    JsonToObject.getObjectByJson(responseJson, responseJsonObject);

        } catch (IOException e) {
            logger.error("Post请求出了些问题");
            throw new RuntimeException("Post请求出了些问题", e);
        }

    }

    /**
     * 只发送post请求
     *
     * @param serviceName
     * @param requestJsonBody
     */
    public static void postByServiceName(String serviceName, String requestJsonBody) {
        //TODO
        String serviceUrl = GetServiceUrl.getUrlByDisUrlAndName("", serviceName);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJsonBody);
        Request request = new Request.Builder().url(serviceUrl).post(body).build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            logger.error("发送post请求失败");
            throw new RuntimeException("发送post请求失败", e);
        }
    }

    /**
     * 根据service url发送json post请求，并获取返回的object
     *
     * @param serviceUrl
     * @param requestJsonBody
     * @param responseObjectClz
     * @param <T>
     * @return
     */
    public static <T> T postByUrlAndGetObject(String serviceUrl, String requestJsonBody, Class<T> responseObjectClz) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJsonBody);
        Request request = new Request.Builder().url(serviceUrl).post(body).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String responseJson = new String(response.body().bytes(), "UTF-8");
            return
                    JsonToObject.getObjectByJson(responseJson, responseObjectClz);
        } catch (IOException e) {
            logger.error("Post请求出了些问题");
            throw new RuntimeException("Post请求出了些问题", e);
        }
    }

    /**
     * 根据service url 发送json post请求
     *
     * @param serviceUrl
     * @param requestJsonBody
     */
    public static void postByServiceUrl(String serviceUrl, String requestJsonBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestJsonBody);
        Request request =
                new Request.
                        Builder().
                        url(serviceUrl).
                        post(body).
                        build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            logger.error("根据URL发送post请求失败");
            throw new RuntimeException("根据URL发送post请求失败", e);
        }
    }

    /**
     * 发送 post请求,并返回一个string
     *
     * @param serviceUrl
     * @param requestBody
     * @return
     */
    public static String postByServiceUrlAndGetString(String serviceUrl, String requestBody) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody);
        Request request = new Request.Builder().url(serviceUrl).post(body).build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return
                    new String(response.body().bytes(), "UTF-8");
        } catch (IOException e) {
            logger.error("获取service url失败");
            throw new RuntimeException("获取service url失败", e);
        }
    }
}
