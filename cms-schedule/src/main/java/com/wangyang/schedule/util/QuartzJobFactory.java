package com.wangyang.schedule.util;

import com.wangyang.common.utils.TemplateUtil;
import com.wangyang.schedule.config.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class QuartzJobFactory implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobMethod = (String)jobExecutionContext.getMergedJobDataMap().get("jobMethod");
        String jobArgs = (String)jobExecutionContext.getMergedJobDataMap().get("jobArgs");

        try {
            Object bean = SpringContextUtil.getBean("articleJob");
            Method method = bean.getClass().getMethod(jobMethod);
            if(jobArgs!=null&&!"".equals(jobArgs)){
                method.invoke(bean,jobArgs);
            }else {
                method.invoke(bean);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
