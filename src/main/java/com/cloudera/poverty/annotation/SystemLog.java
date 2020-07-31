package com.cloudera.poverty.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String value() default "";

    String description() default "";

    FmtType formatType() default FmtType.Map;

    String[] sysParams() default "";

    Class beanType() default Object.class;
}


