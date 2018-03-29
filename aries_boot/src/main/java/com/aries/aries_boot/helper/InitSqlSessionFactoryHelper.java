package com.aries.aries_boot.helper;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * create by aries 2018-3-29
 * <p>
 * 初始化SqlSessionFactory
 */
public class InitSqlSessionFactoryHelper {

    public InitSqlSessionFactoryHelper() {
        String jdbcUrl = ConfigHelper.getJdbcUrl();
        String username = ConfigHelper.getJdbcUsername();
        String password = ConfigHelper.getJdbcPassword();
        String driverName = ConfigHelper.getJdbcDriver();
        //初始化datasource
        DruidDataSource druidDataSource = new DruidDataSource();
        if (null != jdbcUrl || !"".equals(jdbcUrl)) {
            druidDataSource.setUrl(jdbcUrl);
        }

        if (null != username || !"".equals(username)) {
            druidDataSource.setUsername(username);
        }

        if (null != password || !"".equals(password)) {
            druidDataSource.setPassword(password);
        }

        if (null != driverName || !"".equals(driverName)) {
            druidDataSource.setDriverClassName(driverName);
        }

        Environment environment = new Environment("develop", new JdbcTransactionFactory(), druidDataSource);

        Configuration configuration = new Configuration();
        configuration.setEnvironment(environment);
        configuration.addMappers(ConfigHelper.getMapperPackage());
        //开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        BeanHelper.getBeanMap().put(SqlSessionFactory.class, builder.build(configuration));


    }

}
