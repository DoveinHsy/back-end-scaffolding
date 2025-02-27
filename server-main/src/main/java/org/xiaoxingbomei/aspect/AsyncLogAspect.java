package org.xiaoxingbomei.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.task.LogEventListener;


/**
 * 使用后置切入封装需要保存的日志，通过发布订阅模式，发布消息。
 * 说明一下这里可以对请求参数、响应参数以及一些异常信息进行保存，这个就需要根据自己的项目实际情况保存。
 */
@Slf4j
@Aspect
@Component
public class AsyncLogAspect
{

    private final LogEventListener logEventListener;

    public AsyncLogAspect(LogEventListener logEventListener)
    {
        this.logEventListener = logEventListener;
    }


    @Pointcut("@annotation(org.xiaoxingbomei.annotation.AsyncLogRecord)")
    public void asyncLogRecord(){}

    @After("asyncLogRecord()")
    public void logAfter(JoinPoint joinPoint)
    {
        log.info("------------- Async log record start -------------");
        logEventListener.insertBusinessLogCommon(null);
        log.info("------------- Async log record end -------------");

    }

}
