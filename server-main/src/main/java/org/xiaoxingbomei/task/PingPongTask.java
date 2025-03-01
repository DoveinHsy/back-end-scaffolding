package org.xiaoxingbomei.task;


import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.config.apollo.ApolloConfig;
import org.xiaoxingbomei.utils.Exception_Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 专注于组件的心跳检测
 */
@Slf4j
@Component
public class PingPongTask
{
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private RestClient restClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private ApolloConfig apolloConfigService;

    @Autowired
    @Qualifier("mongoTemplateOfLocal")
    private MongoTemplate mongoTemplateOfLocal;

    @Autowired
    @Qualifier("localhostDataSource")
    private DataSource localhostDataSource;

    // 中间件连接状态
    private final AtomicBoolean isRedisConnected        = new AtomicBoolean(false);
    private final AtomicBoolean isMinioConnected        = new AtomicBoolean(false);
    private final AtomicBoolean isElasticConnected      = new AtomicBoolean(false);
    private final AtomicBoolean isKafkaConnected        = new AtomicBoolean(false);
    private final AtomicBoolean isRocketMQConnected     = new AtomicBoolean(false);
    private final AtomicBoolean isApolloConnected       = new AtomicBoolean(false);
    private final AtomicBoolean isMysqlOfLocalConnected = new AtomicBoolean(false); // MySQL 连接状态
    private final AtomicBoolean isMongoOfLocalConnected = new AtomicBoolean(false); // MongoDB 连接状态

    public boolean isRedisConnected()        {return isRedisConnected.get();}
    public boolean isMinioConnected()        {return isMinioConnected.get();}
    public boolean isElasticConnected()      {return isElasticConnected.get();}
    public boolean isKafkaConnected()        {return isKafkaConnected.get();}
    public boolean isRocketMQConnected()     {return isRocketMQConnected.get();}
    public boolean isApolloConnected()       {return isApolloConnected.get();}
    public boolean isMysqlOfLocalConnected() {return isMysqlOfLocalConnected.get();}
    public boolean isMongoOfLocalConnected() {return isMongoOfLocalConnected.get();}


    // 定时搜集并打印中间件连接状态
//    @Scheduled(fixedRate = 200000, initialDelay = 5000)
    public void checkMiddleWareAlive()
    {
        log.info("\n------------------------------------------------\n\t{}{}{}{}{}{}{}{}{}{}",
                "<<Checking middleware connection status>>",
                "\n\tredis-local    \tconnected:\t " + isRedisConnected(),
                "\n\tminio-local    \tconnected:\t " + isMinioConnected(),
                "\n\telasticsearch  \tconnected:\t " + isElasticConnected(),
                "\n\tKafka          \tconnected:\t " + isKafkaConnected(),
                "\n\tRocketMQ       \tconnected:\t " + isRocketMQConnected(),
                "\n\tapollo         \tconnected:\t " + isApolloConnected(),
                "\n\tmysql-local    \tconnected:\t " + isMysqlOfLocalConnected(),
                "\n\tmongodb-local  \tconnected:\t " + isMongoOfLocalConnected(),
                "\n------------------------------------------------\n"
                );
    }

    // 定时检查redis连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkRedisConnection()
    {
        try
        {
            // 简单的get操作来测试redis的连接
            redisTemplate.opsForValue().get("test_redis_connect");

            // 修改redis连接状态
            isRedisConnected.set(true);

            // log.info("=========定时检查组件:redis client connected successfully=========");

        }catch (Exception e)
        {
            // 打印堆栈
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);

            // 修改redis连接状态
            isRedisConnected.set(false);

            log.error("redis client connect failed");
        }
    }

    // 定时检查minio连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkMinioConnection()
    {
        try
        {
            // 检测minio连接
            minioClient.listBuckets();
            // 修改minio连接状态
            isMinioConnected.set(true);
        }
        catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            isMinioConnected.set(false);
            log.info("=========定时检查组件:minio connected failure=========");
        }
    }

    // 定时检查 Elasticsearch 连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkElasticConnection()
    {
        try
        {
            // Elasticsearch 使用 RestClient 发送简单的请求来检查连接
            Response response = restClient.performRequest(new Request("GET", "/"));
            if(response.getStatusLine().getStatusCode()==200)
            {
                isElasticConnected.set(true);
            }else
            {
                isElasticConnected.set(false);
            }

        } catch (Exception e) {
            isElasticConnected.set(false);
            log.error("Elasticsearch connection failed", e);
        }
    }

    // 定时检查 Kafka 连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkKafkaConnection()
    {
        try {
            // Kafka 使用 `kafkaTemplate` 检查连接
            kafkaTemplate.send("test-topic", "ping");  // 尝试发送消息
            isKafkaConnected.set(true);
        } catch (Exception e) {
            isKafkaConnected.set(false);
            log.error("Kafka connection failed", e);
        }
    }

    // 定时检查 Apollo 配置中心连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkApolloConnection()
    {
        try {
            // Apollo 使用 `apolloConfigService` 检查连接
            apolloConfigService.apolloConfig();  // 检查配置
            isApolloConnected.set(true);
        } catch (Exception e) {
            isApolloConnected.set(false);
            log.error("Apollo connection failed", e);
        }
    }

    // 定时检查 MySQL 连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkMysqlConnection() {
        try (Connection connection = localhostDataSource.getConnection()) {
            // 简单的 SQL 查询来测试 MySQL 连接
            connection.createStatement().execute("SELECT 1");
            isMysqlOfLocalConnected.set(true);
        } catch (SQLException e) {
            isMysqlOfLocalConnected.set(false);
            log.error("MySQL connection failed", e);
        }
    }

    // 定时检查 MongoDB 连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkMongoConnection() {
        try {
            // 使用 MongoTemplate 检查 MongoDB 连接
            mongoTemplateOfLocal.getDb().listCollectionNames().first();
            isMongoOfLocalConnected.set(true);
        } catch (Exception e) {
            isMongoOfLocalConnected.set(false);
            log.error("MongoDB connection failed", e);
        }
    }

    // 定时检查 RocketMQ 连接状态
    @Scheduled(fixedDelay = 20000)
    public void checkRocketMQConnection() {
        try {
            // 通过发送一条消息检查 RocketMQ 连接
            rocketMQTemplate.convertAndSend("ping-topic", "ping-message");

            // 如果没有抛出异常，则表示连接成功
            isRocketMQConnected.set(true);
        } catch (Exception e) {
            isRocketMQConnected.set(false);
            log.error("RocketMQ connection failed", e);
        }
    }


}
