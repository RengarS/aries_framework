package com.aries.aries_templet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectToJson {

    private static final ObjectMapper mapper = GetObjectMapper.getMapper();

    private static final Logger logger = LoggerFactory.getLogger(ObjectToJson.class);

    /**
     * 将objec 转成json
     *
     * @param object
     * @return
     */
    public static String getJsonByObject(Object object) {
        try {
            return
                    mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Object 转成 json 失败");
            throw new RuntimeException("Object 转成 json 失败", e);
        }
    }
}
