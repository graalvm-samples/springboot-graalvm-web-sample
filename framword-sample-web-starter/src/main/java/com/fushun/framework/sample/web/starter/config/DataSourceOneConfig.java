package com.fushun.framework.sample.web.starter.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.spring.boot3.autoconfigure.filter.logging.Slf4jLogFilter;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.fushun.framework.dynamic.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class DataSourceOneConfig {

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Bean(name = "firstDataSourceProperties")
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSourceProperties firstDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name="slf4jLogFilterFirst")
    @ConfigurationProperties("spring.datasource.druid.first.filter.slf4j")
    public Slf4jLogFilter slf4jLogFilterFirst() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        return filter;
    }

    @Bean(name = "firstDataSource")
    public DataSource firstDataSource(@Qualifier("firstDataSourceProperties") DataSourceProperties basicProperties, @Qualifier("slf4jLogFilterFirst") Slf4jLogFilter slf4jLogFilterFirst) {
        log.info("firstDataSource");
        DruidDataSourceWrapper druidDataSourceWrapper = new DruidDataSourceWrapper();
        //增加手动设置
        druidDataSourceWrapper.setBasicProperties(basicProperties);
        List<Filter> filterList=druidDataSourceWrapper.getProxyFilters();
        List<Filter> filters= Collections.singletonList(slf4jLogFilterFirst);
        for(Filter filter:filters){
            boolean bool= false;
            for (Filter filter1:filterList){
                if(filter1.getClass()==filter.getClass()){
                    bool=true;
                    break;
                }
            }
            if(!bool){
                druidDataSourceWrapper.autoAddFilters(Collections.singletonList(filter));
            }
        }
        return druidDataSourceWrapper;
    }

    @Bean(name = "secondDataSourceProperties")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSourceProperties secondDataSourceProperties(){
        return new DataSourceProperties();
    }


    @Bean(name="slf4jLogFilterSecond")
    @ConfigurationProperties("spring.datasource.druid.second.filter.slf4j")
    public Slf4jLogFilter slf4jLogFilterSecond() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        return filter;
    }

    @Bean(name = "secondDataSource")
    public DataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties basicProperties, @Qualifier("slf4jLogFilterSecond") Slf4jLogFilter slf4jLogFilter) {
        log.info("secondDataSource");
        DruidDataSourceWrapper druidDataSourceWrapper = new DruidDataSourceWrapper();
        //增加手动设置
        druidDataSourceWrapper.setBasicProperties(basicProperties);
        List<Filter> filterList=druidDataSourceWrapper.getProxyFilters();
        List<Filter> filters= Collections.singletonList(slf4jLogFilter);
        for(Filter filter:filters){
            boolean bool= false;
            for (Filter filter1:filterList){
                if(filter1.getClass()==filter.getClass()){
                    bool=true;
                    break;
                }
            }
            if(!bool){
                druidDataSourceWrapper.autoAddFilters(Collections.singletonList(filter));
            }
        }
        return druidDataSourceWrapper;
    }

    @Primary
    @Bean
    public DataSource dynamicDataSource(@Qualifier("firstDataSource") DataSource firstDataSource,
                                        @Qualifier("secondDataSource") DataSource secondDataSource) {
        log.info("dynamicDataSource");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("first", firstDataSource);
        dataSourceMap.put("second", secondDataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(firstDataSource);
        return dynamicDataSource;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dynamicDataSource) {
//        return new DataSourceTransactionManager(dynamicDataSource);
//    }

    public Resource[] resolveMapperLocations(String[] mapperLocations, ResourcePatternResolver resourceResolver) {
        return Stream.of(Optional.ofNullable(mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location, resourceResolver))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location, ResourcePatternResolver resourceResolver) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }



//    @Bean(name = "sqlSessionFactoryOne")
//    @Primary
//    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("firstDataSource") DataSource firstDataSource)throws Exception{
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(firstDataSource);
//        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
//        String[] mapperLocations = new String[]{
//                "classpath*:/fund/mapper/**/*Mapper.xml",
//                "classpath*:/community/mapper/**/*Mapper.xml",
//                "classpath*:/adminweb/mapper/**/*Mapper.xml"
//        };
//        Resource[] resources=resolveMapperLocations(mapperLocations,pathMatchingResourcePatternResolver);
//        sqlSessionFactory.setMapperLocations(resources);
//        sqlSessionFactory.setPlugins(mybatisPlusInterceptor);
//        return sqlSessionFactory.getObject();
//    }
//
//    @Bean(name = "dataSourceTransactionManagerOne")
//    @Primary
//    public DataSourceTransactionManager dataSourceTransactionManagerOne(@Qualifier("firstDataSource") DataSource firstDataSource){
//        return new DataSourceTransactionManager(firstDataSource);
//    }
//
//    @Bean(name = "sqlSessionTemplateOne")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplateOne(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory sqlSessionFactoryOne)throws Exception{
//        return new SqlSessionTemplate(sqlSessionFactoryOne);
//    }

    ///========================================================================

//    @Bean(name = "sqlSessionFactorySecond")
//    public SqlSessionFactory sqlSessionFactorySecond(@Qualifier("secondDataSource") DataSource secondDataSource)throws Exception{
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(secondDataSource);
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:/fund/mapper/**/*Mapper.xml"));
//        sqlSessionFactory.setPlugins(mybatisPlusInterceptor);
//
//        return sqlSessionFactory.getObject();
//    }
//
//    @Bean(name = "dataSourceTransactionManagerSecond")
//    public DataSourceTransactionManager dataSourceTransactionManagerSecond(@Qualifier("secondDataSource") DataSource secondDataSource){
//        return new DataSourceTransactionManager(secondDataSource);
//    }
//
//    @Bean(name = "sqlSessionTemplateSecond")
//    public SqlSessionTemplate sqlSessionTemplateSecond(@Qualifier("sqlSessionFactorySecond") SqlSessionFactory sqlSessionFactorySecond)throws Exception{
//        return new SqlSessionTemplate(sqlSessionFactorySecond);
//    }

}
