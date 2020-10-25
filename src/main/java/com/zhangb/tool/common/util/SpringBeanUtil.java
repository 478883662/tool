package com.zhangb.tool.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by z9104 on 2020/10/1.
 */
@Component
public class SpringBeanUtil  implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public   void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    private static ApplicationContext getContext() {
        return applicationContext;
    }

    public static Object getBean(String beanId) {
        return SpringBeanUtil.getBean(Object.class, beanId);
    }

    public static <T> T getBean(Class<T> clazz, String beanId) throws ClassCastException {
        ApplicationContext context = SpringBeanUtil.getContext();
        boolean a=context.containsBean(beanId);
        Object bean = null;
        bean = context.getBean(beanId);
        return (T)bean;
    }
}
