package com.aries.aries_boot.resolvers;

import com.aries.aries_boot.annotation.*;
import com.aries.aries_boot.helper.ConfigHelper;
import com.aries.aries_boot.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class RequestAndResponseResolver {

    private static final Logger logger = LoggerFactory.getLogger(RequestAndResponseResolver.class);
    private static final String basePath = Class.class.getClass().getResource("/").getPath();


    /**
     * 请求参数解析
     *
     * @param request
     * @param response
     * @param controllerBean
     * @param actionMethod
     * @return
     */
    public static Object requestParamResolver(HttpServletRequest request, HttpServletResponse response, Object controllerBean, Method actionMethod) {
        //获取actionMethod的所有参数
        Parameter[] parameters = actionMethod.getParameters();
        //请求参数列表
        List<Object> paramList = new LinkedList<>();
        //返回值
        Object result;

        for (Parameter parameter : parameters) {

            if (ClassTypeUtil.isRefType(parameter.getType())) {
                if (parameter.getType() == Model.class) {
                    paramList.add(new Model(request));
                    logger.info("参数：Model已经解析");
                } else if (parameter.getType() == String.class) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {
                        String name = parameter.getDeclaredAnnotation(RequestParam.class).value();
                        paramList.add(request.getParameter(name));
                        logger.info("参数：" + name + "已经解析");
                    }
                } else if (parameter.getType() == HttpSession.class) {
                    //解析session类型的参数
                    paramList.add(request.getSession());
                    logger.info("参数：Session已经解析");
                } else if (parameter.getType() == Cookie[].class) {
                    //解析Cookie类型的参数
                    paramList.add(request.getCookies());
                    logger.info("参数：Cookie[]已经解析");
                } else if (parameter.getType() == HttpServletRequest.class) {
                    //可以访问request
                    paramList.add(request);
                    logger.info("参数：HttpServletRequest已经解析");
                } else if (parameter.getType() == HttpServletResponse.class) {
                    //可以访问Response
                    paramList.add(response);
                    logger.info("参数：HttpServletResponse已经解析");
                } else if (parameter.isAnnotationPresent(RequestStream.class)) {
                    //解析Stream参数
                    byte[] bytes = new byte[request.getContentLength()];
                    try {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(request.getInputStream());
                        bufferedInputStream.read(bytes);
                        paramList.add(SerializableUtils.unSerializable(bytes, parameter.getClass()));
                    } catch (IOException e) {
                        logger.error("解析Stream参数失败");
                        throw
                                new RuntimeException("解析Stream参数失败！", e);
                    }
                } else {
                    if (parameter.isAnnotationPresent(RequestBody.class)) {
                        String name = parameter.getDeclaredAnnotation(RequestBody.class).value();
                        if (!"".equals(name)) {
                            paramList.add(JsonUtil.fromJson(request.getParameter(name), parameter.getType()));
                            logger.info("参数：" + name + "已经解析");
                        } else {
                            paramList.add(JsonUtil.fromJson(request, parameter.getType()));
                        }
                    }

                }
            } else {
                //基本数据类型的参数解析
                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    String name = parameter.getDeclaredAnnotation(RequestParam.class).value();
                    paramList.add(CastUtil.castStringToOtherType(parameter.getType(), request.getParameter(name)));
                    logger.info("参数：" + name + "已经解析");
                }
            }
        }


        paramList.
                parallelStream().
                forEach(param ->
                        logger.info("参数列表中的：" + param.toString()
                        )
                );

        if (paramList.size() == 0) {
            result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
        } else {
            result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, paramList.toArray());
        }


        logger.info("正在调用请求handler的方法");
        //help GC
        paramList = null;

        return result;
    }

    /**
     * 返回值处理
     *
     * @param result
     * @param request
     * @param response
     * @param actionMethod
     * @throws ServletException
     * @throws IOException
     */

    public static void returnResultResolver(Object result, HttpServletRequest request, HttpServletResponse response, Method actionMethod) throws ServletException, IOException {
        //处理返回值
        //如果方法被@ResponseBody标记，则返回json
        if (result != null) {
            if (actionMethod.isAnnotationPresent(ResponseBody.class)) {
                responseBodyResolver(result, response);
            } else if (actionMethod.isAnnotationPresent(StreamBody.class)) {
                //如果被@StreamBody注解就将返回值序列化写出
                byte[] bytes = SerializableUtils.Serializable(result, result.getClass());
                writeBytesOut(bytes, response);

            }
            //如果返回值是string类型的，并且没有被@ResponseBody标记，则返回一个view
            else if (result instanceof String && !actionMethod.isAnnotationPresent(ResponseBody.class)) {
                logger.info("返回Page：" + result + ".jsp中");
                response.sendRedirect(basePath + ConfigHelper.getAppJspPath() + result + ".jsp");
            }
        }

    }


    /**
     * 将json写出到客户端
     *
     * @param result
     * @param response
     * @throws IOException
     */

    public static void responseBodyResolver(Object result, HttpServletResponse response) throws IOException {
        if (result != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(result);
            logger.info("返回值的json体是：" + json);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    /**
     * 将byte[]写出
     *
     * @param bytes
     * @param response
     */
    private static void writeBytesOut(byte[] bytes, HttpServletResponse response) {
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(response.getOutputStream());
            stream.write(bytes);
        } catch (IOException e) {
            logger.error("写出StreamBody返回值失败！");
            throw
                    new RuntimeException("写出StreamBody返回值失败！", e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                logger.error("关闭outPutStream失败！");
            }
        }

    }

}
