package com.aries.aries_templet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonToObject {
    private static final Logger logger = LoggerFactory.getLogger(JsonToObject.class);

    private static final ObjectMapper mapper = GetObjectMapper.getMapper();

    /**
     * @param json 要转成Object的json
     * @param cls  Object的class
     * @param <T>
     * @return 返回的object
     */
    public static <T> T getObjectByJson(String json, Class<T> cls) {

        try {
            return
                    mapper.readValue(json, cls);
        } catch (IOException e) {
            logger.error("Json 转 Object 失败");
            throw new RuntimeException("Json 转 Object 失败", e);
        }
    }

}
