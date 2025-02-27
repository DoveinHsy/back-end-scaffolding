package org.xiaoxingbomei.aspect;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.annotation.RequestLimiting;
import org.xiaoxingbomei.exception.UserException;
import org.xiaoxingbomei.utils.Redis_Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * 用户粒度限流切面
 */
@Aspect
@Component
@Slf4j
public class RequestLimitingAspect
{

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private Redis_Utils redisUtils;

    // 切点
    @Pointcut("@annotation(requestLimiting)")
    public void aspect(RequestLimiting requestLimiting) {}

    // 环绕切面
    @Around("aspect(requestLimiting)")
    public Object around(ProceedingJoinPoint joinPoint, RequestLimiting requestLimiting) throws Throwable
    {

        // 检查redis连接状态
        if (redisUtils.isRedisConnected())
        {
            return handleRequestWithLimiting(joinPoint, requestLimiting);
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
                    "\n\t【aspect】        : \t" + "RequestLimitingAspect",
                    "\n\t【behavior】      : \t" + "接口限流",
                    "\n\t【request url】   : \t" + request.getRequestURL().toString(),
                    "\n\t【http method】   : \t" + request.getMethod(),
                    "\n\t【failure reason】: \t" + "Redis connect exception",
                    "\n----------------------------------------------------------\n");

            return joinPoint.proceed();
        }
    }



//        try
//        {
//            // 尝试与redis进行简单的ping 操作 以检测连接状态
//            redisTemplate.opsForValue().get("test_redis_connect");
//            // 如果redis连接正常，执行限流逻辑
//            return handleRequestWithLimiting(joinPoint, requestLimiting);
//        }
//        // 如果redis连接异常，则放行，不执行限流逻辑
//        catch (RedisConnectionException e)
//        {
//            // 如果是 其他可能的redis数据访问异常
//            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
//
//            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
//            HttpServletRequest request = attributes.getRequest();
//            // 请求ip
//            String ip = request.getRemoteAddr();
//            // 请求uri
//            String uri = request.getRequestURI();
//
//            log.warn("\n----------------------------------------------------------\n{}{}{}{}{}{}{}",
//                    " << Aspect behavior failure >>",
//                    "\n\t【aspect】        : \t" + "RequestLimitingAspect",
//                    "\n\t【behavior】      : \t" + "接口限流",
//                    "\n\t【request url】   : \t" + request.getRequestURL().toString(),
//                    "\n\t【http method】   : \t" + request.getMethod(),
//                    "\n\t【failure reason】: \t" + "Redis connect exception",
//                    "\n----------------------------------------------------------\n");
//
//            return joinPoint.proceed();
//        }
//        catch (PoolException e)
//        {
//            // 如果是 其他可能的redis数据访问异常
//            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
//
//            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
//            HttpServletRequest request = attributes.getRequest();
//            // 请求ip
//            String ip = request.getRemoteAddr();
//            // 请求uri
//            String uri = request.getRequestURI();
//
//            log.warn("\n----------------------------------------------------------\n{}{}{}{}{}{}{}",
//                " << Aspect behavior failure >>",
//                "\n\t【aspect】        : \t" + "RequestLimitingAspect",
//                "\n\t【behavior】      : \t" + "接口限流",
//                "\n\t【request url】   : \t" + request.getRequestURL().toString(),
//                "\n\t【http method】   : \t" + request.getMethod(),
//                "\n\t【failure reason】: \t" + "PoolException exception",
//                "\n----------------------------------------------------------\n");
//
//            return joinPoint.proceed();
//        }
//    }

    // 滑动窗口限流逻辑
    private Object handleRequestWithLimiting(ProceedingJoinPoint joinPoint, RequestLimiting requestLimiting) throws Throwable
    {
        // 获取注解参数
        long period     = requestLimiting.period();
        long limitCount = requestLimiting.count();

        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = attributes.getRequest();

        // 请求ip
        String ip = request.getRemoteAddr();

        // 请求uri
        String uri = request.getRequestURI();

        // redis key
        String key = "req_limiting_" + uri + "_" + ip;

        // 获取ZSetOperations
        ZSetOperations<String,Long> zSetOperations = redisTemplate.opsForZSet();

        // 查询访问次数
        long count = zSetOperations.zCard(key);

        // 判断访问次数
        if (count > limitCount)
        {
            log.info("限流拦截接口：{},用户{},访问超过【{}次/{}s】限制",uri, ip, limitCount, period );
            throw new UserException(GlobalCodeEnum.USER_ACCESS_EXCEED.getCode(), "您的请求太快啦，请稍后重试~");
        }

        // 获取当前时间
        long currentTimeMillis = System.currentTimeMillis();

        // 添加当前时间
        zSetOperations.add(key, currentTimeMillis, currentTimeMillis);

        // 设置过期时间
        redisTemplate.expire(key, period, TimeUnit.SECONDS);

        // 删除窗口外的值
        zSetOperations.removeRangeByScore(key, 0, currentTimeMillis - period * 1000);

        // 放行
        return joinPoint.proceed();
    }

}