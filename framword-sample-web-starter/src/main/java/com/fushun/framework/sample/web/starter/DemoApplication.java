package com.fushun.framework.sample.web.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serializable;

/**
 *
 * <p>一个简单的使用演示</p>
 * <p>1.按需配置application.properties，配置项请参考config-template.properties</p>
 * <p>2.run DemoApplication</p>
 * <p>3.访问http://127.0.0.1:8080/druid</p>
 * <p>4.访问/user/${id}接口，查看SQL、Web、AOP监控效果，如：http://127.0.0.1:8080/user/1</p>
 *
 */
@SpringBootApplication(proxyBeanMethods = false,
		scanBasePackages={
		"com.fushun.framework.sample.web.starter",
				"com.fushun.framework.sample.security",
				"com.fushun.framework.sample.mybatis",
				"com.fushun.framework.sample.jpa",
				"com.fushun.framework"
}
)
public class DemoApplication implements Serializable {

	public static void main(String[] args) {
		System.setProperty("druid.log.stmt.executableSql","true");
		System.setProperty("druid.log.stmt","true");
		System.setProperty("druid.log.conn","false");
		System.setProperty("druid.log.rs","false");
		System.setProperty("org.apache.poi.ss.ignoreMissingFontSystem", "true");
		System.out.println("druid.log.stmt.executableSql:{}"+System.getProperty("druid.log.stmt.executableSql"));
		SpringApplication.run(DemoApplication.class, args);
	}
}
