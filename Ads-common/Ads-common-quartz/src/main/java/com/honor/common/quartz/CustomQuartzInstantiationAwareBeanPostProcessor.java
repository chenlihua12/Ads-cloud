package com.honor.common.quartz;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

@Component
@ConfigurationProperties(prefix = "spring.quartz")
public class CustomQuartzInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    private boolean overwriteExistingJobs;

    public void setOverwriteExistingJobs(boolean overwriteExistingJobs) {
        this.overwriteExistingJobs = overwriteExistingJobs;
    }

    /**
     * 修復bug  OverwriteExistingJobs 属性设置  在boot 2.1.1版本中有，之前的版本有问题
     *
     * @param pvs
     * @param pds
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if (bean instanceof SchedulerFactoryBean) {
            ((SchedulerFactoryBean) bean).setOverwriteExistingJobs(overwriteExistingJobs);
        }
        return super.postProcessPropertyValues(pvs, pds, bean, beanName);
    }
}
