package com.aries.context;

import java.util.HashMap;
import java.util.Map;

public class AriesFrameworkBeanContext {

    private static Map<Class<?>, Object> BEAN_CONTEXT = new HashMap<>();

    public static Map<Class<?>, Object> getBeanContext() {
        return BEAN_CONTEXT;
    }

}
