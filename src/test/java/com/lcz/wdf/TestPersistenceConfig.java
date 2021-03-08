package com.lcz.wdf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * 单元测试使用的数据库配置
 **/
@Profile("test")
@Configuration
@MapperScan("com.lcz.wdf.dao")
public class TestPersistenceConfig {

    public static final String TEST_DB_URL = "jdbc:mysql://127.0.0.1:3306/wdf_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&autoReconnect=true&serverTimezone=Asia/Shanghai";
    public static final String TEST_DB_USER = "root";
    public static final String TEST_DB_PASSWORD = "";

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(TEST_DB_URL);
        dataSourceBuilder.username(TEST_DB_USER);
        dataSourceBuilder.password(TEST_DB_PASSWORD);
        return dataSourceBuilder.build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();
    }

}
