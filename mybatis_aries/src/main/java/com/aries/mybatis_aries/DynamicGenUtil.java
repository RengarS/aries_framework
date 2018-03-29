package com.aries.mybatis_aries;

import com.aries.context.AriesFrameworkBeanContext;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * create by aries  2018-3-28
 * 为DAO接口生成实现类
 */

public class DynamicGenUtil implements MethodInterceptor {

    private static Map<Class<?>, Object> BEAN_MAP = AriesFrameworkBeanContext.getBeanContext();

    @SuppressWarnings("unchecked")
    private static <T> T newInstance(Class<?> clz) {
        Enhancer enhancer = new Enhancer();
        //将其实现接口设置为DAO接口
        enhancer.setInterfaces(new Class[]{clz});
        enhancer.setCallback(new DynamicGenUtil());
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //打开SqlSession
        SqlSession sqlSession = ((SqlSessionFactory) BEAN_MAP.get(SqlSessionFactory.class)).openSession();
        //方法调用结果
        Object res = null;
        //获取mybatis代理
        Object bean = sqlSession.getMapper(o.getClass().getInterfaces()[0]);

        try {
            //保存方法参数类型
            Class<?>[] paramTypes = new Class[objects.length];
            //获取方法参数类型
            for (int i = 0; i < objects.length; i++) {
                paramTypes[i] = objects[i].getClass();
            }
            //根据方法签名获取接口method
            Method interfaceMethod = bean.getClass().getDeclaredMethod(method.getName(), paramTypes);
            //调用接口
            res = interfaceMethod.invoke(bean, objects);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            sqlSession.close();
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <P> P getDAOImpl(Class<?> clz) {
        return (P) DynamicGenUtil.newInstance(clz);
    }

//
//    public static void main(String[] args) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl("jdbc:mysql://116.62.137.207:3306/wlglobal_admin?useUnicode=true&amp;characterEncoding=UTF-8");
//        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        druidDataSource.setUsername("root");
//        druidDataSource.setPassword("I$wkV3U1");
//        Environment environment = new Environment("develop", new JdbcTransactionFactory(), druidDataSource);
//        Configuration configuration = new Configuration();
//        configuration.setEnvironment(environment);
//        configuration.addMappers("com.aries");
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//        DynamicGenUtil.MAP.put(SqlSessionFactory.class, sqlSessionFactoryBuilder.build(configuration));
//        //将接口和instance注入进IoC容器即可  ->  MAP.put(UserDAO.class,com.aries.mybatis_aries.DynamicGenUtil.newInstance(UserDAO.class))
//        UserDAO userDAO = DynamicGenUtil.newInstance(UserDAO.class);
//        User user = userDAO.getUserById("11111");
//        System.out.println(user.getId() + "   " + user.getName() + "   " + user.getAge());
//    }
}
