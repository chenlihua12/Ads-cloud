package com.honor.common.quartz;

import com.honor.common.quartz.annotation.QuartzScheduled;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.utils.Key;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * jobDetail jobTrigger 配置
 */
@Component
public class CustomQuartzBeanDefinitionRegistryPostProcessor implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(name);
            Class<?> beanClass;
            try {
                String beanClassName = beanDefinition.getBeanClassName();
                if (beanClassName == null) {
                    return;
                }
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                throw new BeanDefinitionStoreException(String.format("解析[%s]BeanDefinition出现异常", beanDefinition.toString()), e);
            }


            Map<Method, QuartzScheduled> annotatedMethods = introspectRocketAnnotatedMethod(beanClass);
            if (annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, QuartzScheduled> entry : annotatedMethods.entrySet()) {
                QuartzScheduled quartzScheduled = entry.getValue();

                JobKey jobKey = new JobKey(name, entry.getKey().getName());
                BeanDefinition jobDetailImpl = new RootBeanDefinition(JobDetailImpl.class);
                MutablePropertyValues mpv = jobDetailImpl.getPropertyValues();
                mpv.add("jobClass", DefaultQuartzJobBean.class);
                mpv.add("description", name);
                mpv.add("key", jobKey);
                mpv.add("requestsRecovery", false);
                mpv.add("durability", true);
                beanDefinitionRegistry.registerBeanDefinition(name + entry.getKey().getName() + "JobDetail", jobDetailImpl);

                JobDetailImpl jobDetail = applicationContext.getBean(name + entry.getKey().getName() + "JobDetail", JobDetailImpl.class);

                Calendar cl = Calendar.getInstance();
                cl.setTime(new Date());
                cl.set(Calendar.MILLISECOND, 0);

                //cron
                if (!StringUtils.isEmpty(quartzScheduled.cron())) {

                    BeanDefinition trigger = new RootBeanDefinition(CronTriggerImpl.class);
                    MutablePropertyValues triggerPropertys = trigger.getPropertyValues();
                    triggerPropertys.add("cronExpression", quartzScheduled.cron());
                    //triggerPropertys.add("timeZone", cronExpression.getTimeZone());
                    triggerPropertys.add("misfireInstruction", CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                    triggerPropertys.add("description", entry.getKey().getName());
                    triggerPropertys.add("key", new TriggerKey(name, entry.getKey().getName()));
                    triggerPropertys.add("jobKey", jobDetail.getKey());
                    //triggerPropertys.add("startTime", cl.getTime());

                    beanDefinitionRegistry.registerBeanDefinition(name + entry.getKey().getName() + "JobTrigger", trigger);

                    //间隔执行
                } else if (quartzScheduled.fixedRate() != -1L) {

                    BeanDefinition trigger = new RootBeanDefinition(SimpleTriggerImpl.class);
                    MutablePropertyValues triggerPropertys = trigger.getPropertyValues();
                    triggerPropertys.add("repeatInterval", quartzScheduled.fixedRate());
                    triggerPropertys.add("repeatCount", SimpleTrigger.REPEAT_INDEFINITELY);
                    triggerPropertys.add("misfireInstruction", SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
                    triggerPropertys.add("description", entry.getKey().getName());
                    triggerPropertys.add("key", new TriggerKey(Key.createUniqueName(null), null));
                    triggerPropertys.add("jobKey", jobDetail.getKey());
                    triggerPropertys.add("startTime", cl.getTime());

                    beanDefinitionRegistry.registerBeanDefinition(name + entry.getKey().getName() + "JobTrigger", trigger);
                }
            }
        }
    }

    /**
     * 内省{@link QuartzScheduled}标识的方法
     *
     * @param beanClass
     * @return
     */
    private Map<Method, QuartzScheduled> introspectRocketAnnotatedMethod(Class<?> beanClass) {
        return MethodIntrospector.selectMethods(beanClass,
                (MethodIntrospector.MetadataLookup<QuartzScheduled>) (method) -> method.getAnnotation(QuartzScheduled.class));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
