package org.xiaoxingbomei.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.annotation.ServiceSwitch;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.Constant;
import org.xiaoxingbomei.exception.BusinessException;
import org.xiaoxingbomei.utils.Redis_Utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 *
 */
@Aspect
@Component
@Slf4j
public class ServiceSwitchAspect
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 定义切点，使用了@ServiceSwitch注解的类或方法都拦截
     */
    @Pointcut("@annotation(serviceSwitch)")
    public void aspect(ServiceSwitch serviceSwitch) {}

    @Autowired
    private Redis_Utils redisUtils;

    /**
     * 环绕切面
     * 1、
     * 2、
     * 3、
     */
    @Around("aspect(serviceSwitch)")
    public Object around(ProceedingJoinPoint joinPoint,ServiceSwitch serviceSwitch) throws Throwable
    {

        // 检查redis连接状态
        if (redisUtils.isRedisConnected())
        {
            return handleServiceSwitch(joinPoint, serviceSwitch);
        } else
        {
            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            HttpServletRequest request = attributes.getRequest();

            // 请求ip
            String ip = request.getRemoteAddr();
            // 请求uri
            String uri = request.getRequestURI();

            log.error("\n----------------------------------------------------------\n{}{}{}{}{}{}{}",
                    " << Aspect behavior failure >>",
                    "\n\t【aspect】        : \t" + "ServiceSwitchAspect",
                    "\n\t【behavior】      : \t" + "接口开关",
                    "\n\t【request url】   : \t" + request.getRequestURL().toString(),
                    "\n\t【http method】   : \t" + request.getMethod(),
                    "\n\t【failure reason】: \t" + "redis connect failure",
                    "\n----------------------------------------------------------\n");

            return joinPoint.proceed();
        }

    }

    /**
     * 接口开关 核心实现
     * 1、
     * 2、
     * 3、
     */
    private Object handleServiceSwitch(ProceedingJoinPoint joinPoint, ServiceSwitch serviceSwitch) throws Throwable
    {
        // 获取被代理的方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取被代理的对象
        Object target = joinPoint.getTarget();
        // 获取通知签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try
        {
            // 获取被代理的方法
            Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
            // 获取方法上的注解
            ServiceSwitch annotation = method.getAnnotation(ServiceSwitch.class);
            // 核心业务逻辑
            if (annotation != null)
            {
                // 获取开关默认值
                String switchVal = annotation.switchVal();
                String message = annotation.message();

                // 从redis中取，使用中大家可以按照意愿自行修改。
                String configVal = redisTemplate.opsForValue().get(Constant.SwitchConfigCode.XX_SWITCH);

                // 对比默认值与redis中值，
                if (switchVal.equals(configVal))
                {
                    // 开关打开，则返回提示。
                    return new GlobalEntity(null,String.valueOf(HttpStatus.FORBIDDEN.value()), message,"功能暂未开放","功能已关闭");
                }
            }
            // 放行
            return joinPoint.proceed(args);

        } catch (Throwable e)
        {
            throw new BusinessException(GlobalCodeEnum.ERROR.getCode(),e.getMessage());
        }
    }

}
