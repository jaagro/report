package com.jaagro.report.biz.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.jaagro.report.biz.config.cat.CatMybatisPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author yj
 * @date 2019/3/27 14:42
 */
@Configuration
@MapperScan(basePackages = "com.jaagro.report.biz.mapper.cbs", sqlSessionTemplateRef= "cbsSqlSessionTemplate")
public class CbsDataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource.cbs")
    @Bean(name = "cbsDataSource")
    public DataSource cbsDataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "cbsSqlSessionFactoryBean")
    public SqlSessionFactoryBean cbsSqlSessionFactoryBean(@Qualifier("cbsDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //dataSource
        sqlSessionFactoryBean.setDataSource(dataSource);
        //mybatisPlugin
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});
        //mapperLocation
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/cbs/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResources("classpath*:/mybatis/mybatis_config.xml")[0]);
        return sqlSessionFactoryBean;
    }
    @Bean(name = "cbsSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("cbsSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
