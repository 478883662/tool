package com.zhangb.tool.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * hutool工具db类配合使用的实体类注解
 * Created by z9104 on 2020/9/25.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface DbTableAnnotation {
    /**
     * 表名
     * @return
     */
    String value();
}
