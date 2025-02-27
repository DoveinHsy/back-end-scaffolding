package org.xiaoxingbomei.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.vo.BusinessLogCommon;

/**
 * 日志 发布事件
 */
@Slf4j
@Component
public class LogEventPublisher
{
    //
    private final ApplicationContext applicationContext;

    //
    public LogEventPublisher(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    // 事件发布方法
    public void pushListener(BusinessLogCommon businessLogCommon)
    {
        applicationContext.publishEvent(businessLogCommon);
    }

}
