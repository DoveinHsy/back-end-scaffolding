package org.xiaoxingbomei.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.task.PingPongTask;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * 1、检查redis连接状态
 * 2、
 */
@Component
public class Redis_Utils
{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
//    @Lazy
    private PingPongTask pingPongTask;

    // ============================= 场景 =============================
    // 检查redis连接状态
    public boolean isRedisConnected()
    {
        return pingPongTask.isRedisConnected();
    }

    // ============================= String =============================

    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     * @return true 成功，false 失败
     */
    public boolean StringSet(String key, Object value) 
    {
        try 
        {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置带有过期时间的 key 的值
     *
     * @param key        键
     * @param value      值
     * @param timeout    时间
     * @param timeUnit   时间单位
     * @return true 成功，false 失败
     */
    public boolean StringSetWithExpire(String key, Object value, long timeout, TimeUnit timeUnit) 
    {
        try 
        {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    public Object StringGet(String key) 
    {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除指定 key
     *
     * @param key 键
     * @return true 成功，false 失败
     */
    public boolean StringDelete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================= Hash =============================

    /**
     * 设置 Hash 中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void hashSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取 Hash 中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取整个 Hash 的值
     *
     * @param key 键
     * @return Hash 的值
     */
    public Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // ============================= List =============================

    /**
     * 向 List 左边插入一个元素
     *
     * @param key   键
     * @param value 值
     */
    public void listLeftPush(String key, Object value)
    {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向 List 右边插入一个元素
     *
     * @param key   键
     * @param value 值
     */
    public void listRightPush(String key, Object value)
    {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 从 List 左边弹出一个元素
     *
     * @param key 键
     * @return 弹出的值
     */
    public Object listLeftPop(String key)
    {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 获取 List 中的所有元素
     *
     * @param key 键
     * @return List 的值
     */
    public List<Object> listGetAll(String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    // ============================= Set =============================

    /**
     * 向 Set 中添加一个元素
     *
     * @param key   键
     * @param value 值
     */
    public void setAdd(String key, Object value)
    {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 判断 Set 中是否包含某个值
     *
     * @param key   键
     * @param value 值
     * @return true 包含，false 不包含
     */
    public boolean setContains(String key, Object value)
    {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取 Set 中的所有元素
     *
     * @param key 键
     * @return Set 的值
     */
    public Set<Object> setGetAll(String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    // ============================= Sorted Set =============================

    /**
     * 向 Sorted Set 中添加一个元素
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     */
    public void ZSetAdd(String key, Object value, double score)
    {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取 Sorted Set 中指定分数范围的元素
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return 元素集合
     */
    public Set<Object> ZSetRangeByScore(String key, double min, double max)
    {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取 Sorted Set 中前 N 名的元素
     *
     * @param key 键
     * @param count 前 N 名
     * @return 元素集合
     */
    public Set<Object> ZSetTop(String key, int count) 
    {
        return redisTemplate.opsForZSet().reverseRange(key, 0, count - 1);
    }

}
