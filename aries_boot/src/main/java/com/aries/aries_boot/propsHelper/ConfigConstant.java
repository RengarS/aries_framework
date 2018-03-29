package com.aries.aries_boot.propsHelper;

/**
 * Created by aries on 2017/6/3.
 * 提供相关配置项常量
 */
public interface ConfigConstant {
    String CONFIG_FILE = "aries.properties";
    String JDBC_DRIVER = "aries.framework.jdbc.driver";
    String JDBC_URL = "aries.framework.jdbc.url";
    String JDBC_USERNAME = "aries.framework.jdbc.username";
    String JDBC_PASSWORD = "aries.framework.jdbc.password";
    //项目的基础包名
    String APP_BASE_PACKAGE = "aries.framework.app.base_package";
    //表示JSP的基础路径
    String APP_JSP_PATH = "aries.framework.app.jsp_path";
    //表示静态资源文件的基础路径  比如  js css  图片
    String APP_ASSET_PATH = "aries.framework.app.asset_path";

    String MAPPER_PACKAGE = "aries.framework.mapper.basePackage";

}
