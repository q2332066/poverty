package com.cloudera.poverty.base.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 手动获取Bean实例
 */
@Component
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String beanName) {

        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {

        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {

        BeanFactory.applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}