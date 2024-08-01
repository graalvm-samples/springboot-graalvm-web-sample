package com.fushun.framework.sample.web.starter.config;


import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.util.util.JsonUtil;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * lambda 表达式注入到graal中
 * @date 2023/8/18 11:53
 */
public class LambdaRegistrationFeature implements Feature {

    Logger log= LoggerFactory.getLogger(this.getClass());


    private Class<?> getTableNameByAnntation(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        log.info("beanClassName:{}",beanClassName);
        try {
            Class<?> aClass = Class.forName(beanClassName);
            Service annotation = aClass.getAnnotation(Service.class);
            if (ObjectUtil.isNotNull(annotation)) {
                return aClass;
            }
            RestController annotation2 = aClass.getAnnotation(RestController.class);
            if (ObjectUtil.isNotNull(annotation2)) {
                return aClass;
            }
            Controller annotation3 = aClass.getAnnotation(Controller.class);
            if (ObjectUtil.isNotNull(annotation3)) {
                return aClass;
            }
            //获取到注解name的值并返回
            return null;
        } catch (ClassNotFoundException e) {
            log.error("error",e);
        }
        return null;
    }
    @Override
    public void duringSetup(DuringSetupAccess access) {
        // TODO 这里需要将lambda表达式所使用的成员类都注册上来,具体情况视项目情况而定,一般扫描@Controller和@Service的会多点.
        Set<Class<?>> classes= new HashSet<>();
        log.info("LambdaRegistrationFeature");
        // 不使用默认的TypeFilter
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        // 添加扫描规律规则，这里指定了内置的注解过滤规则
        provider.addIncludeFilter(new AnnotationTypeFilter(Service.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

        // 扫描指定包，如果有多个包，这个过程可以执行多次
        String[] packages = new String[]{ "com.fushun.framework.sample.web.starter","com.fushun.framework.sample.security",
                "com.fushun.framework.sample.mybatis","com.fushun.framework.sample.jpa","com.fushun.framework"};
        for (String aPackage : packages) {
            log.info("aPackage:{}",aPackage);
            Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(aPackage);
            log.info("beanDefinitionSet:{}",beanDefinitionSet.size());
            beanDefinitionSet.forEach(beanDefinition -> {
                Class<?> tableName = getTableNameByAnntation(beanDefinition);
                if(ObjectUtil.isNotNull(tableName)){
                    classes.add(tableName);
                    log.info("tableName:{}",tableName.toString());
                }
            });
        }
        log.info("classes:{}", JsonUtil.toJson(classes));
        for(Class<?> clazz :classes){
            RuntimeSerialization.registerLambdaCapturingClass(clazz);
        }

    }

}
