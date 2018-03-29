package com.aries.aries_boot.helper;

import com.aries.aries_boot.annotation.Autowired;
import com.aries.aries_boot.util.CollectionUtil;
import com.aries.aries_boot.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * Created by aries on 2017/6/3.
 * 依赖注入助手类
 */
public class IocHelper {


    private static final Logger logger = LoggerFactory.getLogger(IocHelper.class);

    public IocHelper() {


        //获取ioc容器中所有的class
        Set<Class<?>> CLASS_SET = BeanHelper.getBeanMap().keySet();
        Map<Class<?>, Object> BEAN_MAP = BeanHelper.getBeanMap();
        //如果class set不为空
        if (CollectionUtil.isNotEmpty(CLASS_SET)) {
            //遍历class set
            for (Class<?> clazz : CLASS_SET) {
                //获取class中所有的field
                Field[] fields = clazz.getDeclaredFields();
                //遍历field
                for (Field field : fields) {
                    //如果field被Autowired注解
                    if (field.isAnnotationPresent(Autowired.class)) {
                        //field赋值
                        try {
                            ReflectionUtil.setField(
                                    BEAN_MAP.get(clazz), field, BEAN_MAP.get(field.getType())
                            );

//                            logger.debug("正在给Field：" + clazz.getName() + "." + field.getName() + "赋值为" + BEAN_MAP.get(field.getType()).toString());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("field" + field.toString() + "赋值出错！", e);
                        }
                    } else {
                        continue;
                    }
                }
            }

        }
    }
}



