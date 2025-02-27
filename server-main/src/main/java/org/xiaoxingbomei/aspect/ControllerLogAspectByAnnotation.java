//package org.xiaoxingbomei.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import com.alibaba.fastjson.JSON;
//
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 统一日志处理：Controller层 通过注解实现
// */
//@Aspect
//@Component
//public class ControllerLogAspectByAnnotation
//{
//
//
//    private static Logger log = LoggerFactory.getLogger(ControllerLogAspectByAnnotation.class);
//
//    /**
//     * 切入点
//     */
//    @Pointcut("@annotation(org.xiaoxingbomei.annotation.ControllerLog)")
//    public void reqMethodInfo() {}
//
//
//    @Around("reqMethodInfo()")
//    public Object doMethodInfo(ProceedingJoinPoint point) throws Throwable
//    {
//
//
//        HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        String servletPath = request == null ? "" : request.getServletPath();
//        Object[] args = point.getArgs();
//        if (args != null && args.length > 0)
//        {
//            log.info("【本系统】【入参】访问路径:[{}]\n,入参:[{}]", servletPath, JSON.toJSON(args[0]));
//        } else
//        {
//            log.info("【本系统】【入参】访问路径:[{}]", servletPath);
//        }
//
//
//        Object result;
//        StopWatch stopWatch = new StopWatch();
//
//
//        stopWatch.start();
//
//
//        result = point.proceed();
//        stopWatch.stop();
//        log.info("【本系统】【出参】访问路径为:[{}]\n接口耗时:[{}]\n出参为:[{}]\n", servletPath, stopWatch.getTotalTimeSeconds()+" seconds",JSON.toJSONString(result));
//
//
//        return result;
//    }
//
//
//
//
//}