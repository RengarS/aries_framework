package com.aries.aries_boot.util;


/**
 * 转型操作工具类
 *
 * @author huangyong
 * @since 1.0.0
 */
public final class CastUtil {

    /**
     * 转为 String 型
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转为 String 型（提供默认值）
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为 double 型
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转为 double 型（提供默认值）
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 转为 long 型
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转为 long 型（提供默认值）
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为 int 型
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转为 int 型（提供默认值）
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 转为 boolean 型
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转为 boolean 型（提供默认值）
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

    /**
     * @param clazz
     * @param comm
     * @param <T>
     * @return
     */
    public static <T> T castStringToOtherType(Class<?> clazz, String comm) {
        if (clazz.getName().equals("int") || clazz.getName().equals("java.lang.Integer")) {
            //return false;
            return (T) Integer.valueOf(comm);
        } else if (clazz.getName().equals("long") || clazz.getName().equals("java.lang.Long")) {
            return (T) Long.valueOf(comm);
        } else if (clazz.getName().equals("boolean") || clazz.getName().equals("java.lang.Boolean")) {
            return (T) Boolean.valueOf(comm);
        } else if (clazz.getName().equals("byte") || clazz.getName().equals("java.lang.Byte")) {
            return (T) Byte.valueOf(comm);
        } else if (clazz.getName().equals("short") || clazz.getName().equals("java.lang.Short")) {
            return (T) Short.valueOf(comm);
        } else if (clazz.getName().equals("double") || clazz.getName().equals("java.lang.Double")) {
            return (T) Double.valueOf(comm);
        } else if (clazz.getName().equals("float") || clazz.getName().equals("java.lang.Float")) {
            return (T) Float.valueOf(comm);
        } else {
            return null;
        }

    }
}