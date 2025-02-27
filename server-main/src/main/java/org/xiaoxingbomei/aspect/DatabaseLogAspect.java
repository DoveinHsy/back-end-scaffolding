package org.xiaoxingbomei.aspect;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xiaoxingbomei.annotation.RequestLimiting;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 统一日志处理：数据库操作
 * 使用方法：
 *
 * 在微服务中，为了更好的了解接口的运行状态，我们经常会通过日志查看接口的入参、出参、调用地址、调用方法和接口的响应时间。
 * 通过AOP面向切面编程，零侵入完成对接口信息的监控，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。
 *
 * `@Aspect`:作用是把当前类标识为一个切面供容器读取
 *
 * `@Pointcut`：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名
 *
 * `@Around`：环绕增强，相当于MethodInterceptor
 *
 * `@AfterReturning`：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 *
 * `@Before`：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 *
 * `@AfterThrowing`：异常抛出增强，相当于ThrowsAdvice
 *
 * `@After`: final增强，不管是抛出异常或者正常退出都会执行
 */
//@Aspect
//@Component
//@Slf4j
//public class DatabaseLogAspect
//{
//
//    /**
//     * 切入点
//     */
//    @Pointcut("execution(public * org.xiaoxingbomei.dao..*.*(..))")
////    @Pointcut("execution(public * org.xiaoxingbomei.config.mybatis.MybatisLogInterceptor.intercept())")
//    public void DatabaseLogAspectByPath() {}
//
//    /**
//     * before
//     * 1、
//     * 2、
//     */
//    @Before("DatabaseLogAspectByPath()")
//    public void logBefore(JoinPoint joinPoint)
//    {
//        String className    =   joinPoint.getSignature().getDeclaringTypeName();
//        String methodName   =   joinPoint.getSignature().getName();
//        Object[] args       =   joinPoint.getArgs();
//        log.info("\n----------------------------------------------------------\n\t{}{}{}{}{}",
//        " << dao before aspect info >>",
//        "\n\t【className】    :\t"    +   className,
//        "\n\t【methodName】   :\t"    +   methodName,
//        "\n\t【arguments】    :\t"    +   Arrays.toString(args),
//        "\n----------------------------------------------------------\n"
//                );
//
//    }
//
//
//}
