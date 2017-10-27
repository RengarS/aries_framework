package com.aries.aries_templet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * create by aries on 2017-9-20
 * 获取object mapper实例
 */
public class GetObjectMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
