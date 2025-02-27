package org.xiaoxingbomei.config.shceduled;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.xiaoxingbomei.utils.SpringContext_Utils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 定时任务的执行类
 * 实现Runnable接口实现类，被定时任务线程池调用，用来执行指定bean里面的方法
 */
@Slf4j
public class ScheduleRunnable implements Runnable
{

    private String beanName;

    private String methodName;

    private String params;

    public ScheduleRunnable(String beanName, String methodName) {
        this(beanName, methodName, null);
    }

    public ScheduleRunnable(String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    public void run() {
        log.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();

        try {
            Object target = SpringContext_Utils.getBean(beanName);

            Method method = null;
            if (CharSequenceUtil.isNotEmpty(params)) {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (CharSequenceUtil.isNotEmpty(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            log.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, params), ex);
        }

        long times = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleRunnable that = (ScheduleRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode()
    {
        if (params == null)
        {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, params);
    }
}