package com.aries.aries_boot.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;

import java.util.HashMap;
import java.util.Map;

public class StreamParamUtil {

    private static Map<Class, Schema> classSchemaMap = new HashMap<>();

    public static Map<Class, Schema> getClassSchemaMap() {
        return classSchemaMap;
    }


    public static <T> byte[] transferObjToStream(Object object, Class<T> clazz) {

        Schema schema = classSchemaMap.get(clazz);

        return
                ProtobufIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

    }

    public static <T> Object transferStreamToObj(byte[] bytes, Class<T> tClass) {

        Schema schema = classSchemaMap.get(tClass);
        Object object = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, object, schema);
        return object;

    }
}
