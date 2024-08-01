package com.fushun.framework.sample.web.starter.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * SecurityJsonNativeConfiguration
 */
@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(PyRuntimeHintsRegistrar.class)
public class PyNativeConfiguration {


}
