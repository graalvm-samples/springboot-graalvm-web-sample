package com.fushun.framework.sample.jpa.configurer;

import com.fushun.framework.jpa.CustomerRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.fushun.framework.sample.jpa.dao", repositoryBaseClass = CustomerRepositoryImpl.class)
@EntityScan(basePackages = "com.fushun.framework.sample.jpa.model")
public class DaoConfig {

}
