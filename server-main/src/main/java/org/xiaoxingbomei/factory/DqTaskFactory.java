package org.xiaoxingbomei.factory;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.service.TaskFactory;

//@Service
//public class DqTaskFactory implements TaskFactory {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Value("${damp.redis.key.dq_task}")
//    private String DQ_TASK;
//
//    @Override
//    public void pullTaskToRedis(SysTaskCatchDTO dto) {
//        RedisOperations<String, String> operations = redisTemplate.opsForList().getOperations();
//        String dtoString = JSON.toJSONString(dto);
//        operations.opsForList().rightPush(DQ_TASK, dtoString);
//    }
//
//    @Override
//    public void delTaskFromRedis(SysTaskCatchDTO dto) {
//        String dtoString = JSON.toJSONString(dto);
//        redisTemplate.opsForList().remove(DQ_TASK, 0, dtoString);
//    }
//}