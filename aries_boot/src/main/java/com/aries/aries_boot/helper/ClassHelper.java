package com.aries.aries_boot.helper;


import com.aries.aries_boot.annotation.Controller;
import com.aries.aries_boot.annotation.Repository;
import com.aries.aries_boot.annotation.Service;
import com.aries.aries_boot.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/3.
 * 类操作助手类
 */
public final class ClassHelper {
    /**
     * 定义类集合（用于存放所有被加载的类）
     */
    private static Set<Class<?>> CLASS_SET = null;
    private static final Logger logger = LoggerFactory.getLogger(ClassHelper.class);


    public ClassHelper() {
        logger.debug("ClassHelper已经启动");
        String basePackage = ConfigHelper.getAppBasePackage();

        System.out.println("basePackage :" + basePackage);

        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下所有类
     */
    public static Set<Class<?>> getClasseSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下所有service类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下所欲controller类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getRepositoryClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Repository.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下所有Bean类包括service controller等
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getRepositoryClassSet());
        return beanClassSet;
    }

}
