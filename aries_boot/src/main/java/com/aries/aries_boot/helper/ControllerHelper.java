package com.aries.aries_boot.helper;


import com.aries.aries_boot.annotation.Controller;
import com.aries.aries_boot.annotation.RequestMapping;
import com.aries.aries_boot.bean.Handler;
import com.aries.aries_boot.bean.Request;
import com.aries.aries_boot.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/4.
 * 控制器助手类
 */
public final class ControllerHelper {
    /**
     * 用于存放请求与处理器的的映射关系（简称 requestMapping map）
     */
    private static final Map<Request, Handler> REQUESTMAPPING_MAP = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ControllerHelper.class);

    public static Map<Request, Handler> getRequestMap() {
        return REQUESTMAPPING_MAP;
    }

    public ControllerHelper() {
        //获取ioc容器中所有的class
        Set<Class<?>> controllerClassSet = BeanHelper.getBeanMap().keySet();
        //如果class set不为空
        if ((CollectionUtil.isNotEmpty(controllerClassSet))) {
            //便利 class set
            for (Class<?> controllerClass : controllerClassSet) {
                //如果当前类没有被@Controller注解    do nothing
                if (!controllerClass.isAnnotationPresent(Controller.class)) {
                    continue;
                }
                //如果被@Controller注解了
                else if (controllerClass.isAnnotationPresent(Controller.class)) {
                    Method[] methods = controllerClass.getDeclaredMethods();
                    //遍历所有的method    如果被@RequestMapping注解了，放进requestMapping  map
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
                            REQUESTMAPPING_MAP.put(new Request(mapping.method().toUpperCase(), mapping.value()), new Handler(controllerClass, method));
                            logger.debug(mapping.value() + ":" + mapping.method() + "已经注入到RequestMapping map");

                            System.out.println(mapping.value() + ":" + mapping.method() + "已经注入到RequestMapping map");
                        } else {
                            continue;
                        }
                    }
                }

            }
        }
        logger.debug("RequestMapping_map的size：" + REQUESTMAPPING_MAP.size());
    }


    /**
     * 获取Handler
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return REQUESTMAPPING_MAP.get(request);
    }
}
