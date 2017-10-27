package com.aries.aries_boot.util;

/**
 * create by aries 2017-9-17
 * 参数类型判定  引用类型 or not
 */
public class ClassTypeUtil {
    /**
     * 类型判定,如果是基本类型，return false
     *
     * @param clazz
     * @return
     */
    public static boolean isRefType(Class<?> clazz) {
        if (clazz.getName().equals("int") || clazz.getName().equals("java.lang.Integer")) {
            return false;
        } else if (clazz.getName().equals("long") || clazz.getName().equals("java.lang.Long")) {
            return false;
        } else if (clazz.getName().equals("boolean") || clazz.getName().equals("java.lang.Boolean")) {
            return false;
        } else if (clazz.getName().equals("byte") || clazz.getName().equals("java.lang.Byte")) {
            return false;
        } else if (clazz.getName().equals("short") || clazz.getName().equals("java.lang.Short")) {
            return false;
        } else if (clazz.getName().equals("double") || clazz.getName().equals("java.lang.Double")) {
            return false;
        } else if (clazz.getName().equals("float") || clazz.getName().equals("java.lang.Float")) {
            return false;
        } else if (clazz.getName().equals("char") || clazz.getName().equals("java.lang.Character")) {
            return false;
        } else {
            return true;
        }
    }


}
