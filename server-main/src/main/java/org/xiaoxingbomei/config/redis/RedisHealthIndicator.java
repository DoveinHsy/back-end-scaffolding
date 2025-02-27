package org.xiaoxingbomei.config.redis;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义redis健康检查器
 * 通过访问 /actuator/health  即可查看
 */
@Component
public class RedisHealthIndicator implements HealthIndicator
{

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisHealthIndicator(RedisConnectionFactory redisConnectionFactory)
    {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public Health health()
    {
        try
        {
            redisConnectionFactory.getConnection().ping();
            return Health.up().build();
        } catch (Exception e)
        {
            return Health.down(e).withDetail("message", "Redis连接失败").build();
        }
    }
}
