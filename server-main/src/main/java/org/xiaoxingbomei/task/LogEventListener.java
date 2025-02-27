package org.xiaoxingbomei.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.service.LogService;
import org.xiaoxingbomei.vo.BusinessLogCommon;

/**
 * 日志 监听事件
 */
@Slf4j
@Component
public class LogEventListener {

    private final LogService logService;

    public LogEventListener(LogService logService)
    {
        this.logService = logService;
    }

    // 开启线程池异步
    @Async("asyncExecutor")

    // 开启监听-业务日志
    @EventListener(BusinessLogCommon.class)
    public void insertBusinessLogCommon(BusinessLogCommon event)
    {
        // 异步插座-脱敏

        // 异步插座-xxx

        // 异步落库至数据库
        log.info("=====异步保存到数据库======");
        logService.insertBusinessLogCommon(event);
    }

    // 开启监听-xx日志

    // 开启监听-xx日志

}
