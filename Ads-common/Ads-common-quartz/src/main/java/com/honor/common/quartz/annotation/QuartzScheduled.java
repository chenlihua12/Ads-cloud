package com.honor.common.quartz.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzScheduled {

    String cron() default "";

    /**
     * 间隔执行【毫秒】
     * @return
     */
    long fixedRate() default -1L;

    /**
     * 是否支持并发，默认不支持
     *
     * @return
     */
    boolean concurrent() default false;
}
