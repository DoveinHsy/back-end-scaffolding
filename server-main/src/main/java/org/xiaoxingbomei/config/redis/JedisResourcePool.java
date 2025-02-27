package org.xiaoxingbomei.config.redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisResourcePool
{
    private JedisPool jedisPool;

    public JedisResourcePool(String host, int port, String password) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        this.jedisPool = new JedisPool(poolConfig, host, port, 5000, password);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}