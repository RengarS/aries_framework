package com.aries.aries_boot.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * JSON 工具类
 *
 * @author huangyong
 * @since 1.0.0
 */
public final class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 POJO 转为 JSON
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 转为 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            logger.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

    /**
     * 将流转成json
     *
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(HttpServletRequest request, Class<T> clazz) {
        int length = request.getContentLength();
        byte[] bytes = new byte[length];
        BufferedInputStream inputStream = null;
        String json = null;
        T pojo;
        try {
            inputStream = new BufferedInputStream(request.getInputStream(), length + 1);
            inputStream.read(bytes);
            json = new String(bytes, "UTF-8");
            System.out.println(json);
            pojo = OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("Json参数解析失败");
            throw new RuntimeException("Json参数解析失败！" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭BufferedInputStream失败", e);
                }
            }
        }

        return pojo;
    }
}
