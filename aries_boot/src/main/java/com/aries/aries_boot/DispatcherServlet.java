package com.aries.aries_boot;


import com.aries.aries_boot.bean.Handler;
import com.aries.aries_boot.helper.*;
import com.aries.aries_boot.resolvers.RequestAndResponseResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 请求转发器
 * Created by Aries on 2017/7/23.
 */
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void init(ServletConfig servletConfig) throws ServletException {
        /**
         * 初始化相关Helper类
         */
        try {
            logger.debug("DispatcherServlet  init   ing......");
            new ClassHelper();
            new InitSqlSessionFactoryHelper();
            new BeanHelper();
            new IocHelper();
            new ControllerHelper();
            //获取ServletContext对象（用于注册servlet）
            ServletContext servletContext = servletConfig.getServletContext();
            //注册jsp的servlet
            ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
            jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
            //注册静态资源的默认servlet
            ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
            defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
            logger.debug("DispatcherServlet初始化完毕");
        } catch (Exception e) {
//            logger.error("初始化失败");
            e.printStackTrace();
            throw new RuntimeException("DispatcherServlet初始化失败", e.getCause());
        }

    }

    /**
     * 重写service
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = request.getMethod().toUpperCase();
        String requestPath = request.getPathInfo();
        //获取action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        logger.debug("请求路径：" + requestPath + "  请求方法：" + requestMethod);

        if (handler == null) {
            logger.debug("没有找到与请求对应的方法");
            throw
                    new RuntimeException("没有找到与请求对应的方法");
        } else {
            logger.debug("处理请求的方法是：" + handler.getControllerClass().getName() + "." + handler.getActionMethod().getName());
            //获取Controller类及其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //获取request对应的方法
            Method actionMethod = handler.getActionMethod();
            Object result = RequestAndResponseResolver.requestParamResolver(request, response, controllerBean, actionMethod);

            logger.debug("处理返回值中...");
            //处理返回值
            RequestAndResponseResolver.returnResultResolver(result, request, response, actionMethod);
        }
    }


}
