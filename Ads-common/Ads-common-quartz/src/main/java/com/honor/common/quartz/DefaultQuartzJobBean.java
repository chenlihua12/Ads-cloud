package com.honor.common.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DefaultQuartzJobBean extends QuartzJobBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        Object target = applicationContext.getBean(jobExecutionContext.getJobDetail().getKey().getName());
        Class clazz = target.getClass();
        try {
            Method method = clazz.getMethod(jobExecutionContext.getJobDetail().getKey().getGroup());
            method.invoke(target);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
