package com.aries.aries_boot.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * Created by Administrator on 2017/6/3.
 */
public final class ArrayUtil {
    /**
     * 判断数组是否非空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isNotEmpty(array);
    }

    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
