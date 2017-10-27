package com.aries.aries_boot.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializableUtils {
    //Class和对应的Schema 的映射 map
    private static Map<Class<?>, RuntimeSchema> SCHEMA_MAP = new ConcurrentHashMap<>();

    /**
     * 将object 序列化成byte[]
     *
     * @param object
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> byte[] Serializable(Object object, Class<T> tClass) {
        RuntimeSchema schema = getSchema(tClass);
        byte[] bytes = ProtobufIOUtil.toByteArray(object, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return
                bytes;
    }

    /**
     * 将 byte[] 反序列化成object
     *
     * @param bytes
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T unSerializable(byte[] bytes, Class<T> tClass) {
        RuntimeSchema schema = getSchema(tClass);
        Object object = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, object, schema);
        return
                (T) object;
    }

    /**
     * 获取schema
     *
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> RuntimeSchema getSchema(Class<T> tClass) {
        RuntimeSchema schema = SCHEMA_MAP.get(tClass);
        if (schema == null) {
            SCHEMA_MAP.putIfAbsent(tClass, RuntimeSchema.createFrom(tClass));
            schema = SCHEMA_MAP.get(tClass);
        }

        return
                schema;
    }
}
