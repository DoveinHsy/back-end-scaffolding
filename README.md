# back-end-scaffolding
# 概述

- 工程简介：

|         工程          |         备注          |      默认端口      | 启动方式                                                                                       |
|:-------------------:|:-------------------:|:--------------:|--------------------------------------------------------------------------------------------|
|    server-parent    |     父工程，全局依赖管理      |       /        | /                                                                                          |
|    server-common    |        公共模块         |       /        | /                                                                                          |
|     server-main     |      主工程，脚手架核心      |     28920      | 非必要常规启动                                                                                    |
|    server-eureka    |        注册中心         |     28921      | 非必要常规启动                                                                                    |
|     server-api      |         网关          |     28922      | 非必要常规启动                                                                                    |
|      server-es      |        搜索服务         |     28923      | 非必要常规启动                                                                                    |
|      server-mq      |        消息服务         |     28924      | 非必要常规启动                                                                                    |
|     server-sql      |        sql服务        |     28925      | 非必要常规启动                                                                                    |
|     server-auth     |        权限服务         |     28927      | 非必要常规启动                                                                                    |
|       apollo        |        三个服务         | 8070、8080、8090 | 必要:./demo.sh start/stop                                                                    |
|        minio        |        对象存储         |      9000      | 非必要:./minio.exe server data                                                                |
|        redis        |        缓存数据库        |      6379      | 非必要:redis-server.exe                                                                       |
|        mysql        |         数据库         |      3306      | 必要:跟随系统                                                                                    |
|       mongodb       |         数据库         |     27017      | 非必要:跟随系统                                                                                   |
|    elasticsearch    |        搜索引擎         |      9200      | 非必要:.\bin\elasticsearch.bat                                                                |
|       kibanna       |        es可视化        |      5601      | 非必要:./bin/kibana.bat                                                                       |
|        kafka        |        消息队列         |      9092      | 非必要：.\bin\windows\kafka-server-start.bat .\config\server.properties                        |
|      zookeeper      |       管理kafka       |      2181      | 非必要：zkServer (配置过全局变量)                                                                     |
| rocketmq-nameserver | rocketmq-nameserver |      9876      | 非必要：start mqnamesrv.cmd(启动rocketmq的前置)                                                     |
|      rockermq       |      rockermq       |     10911      | 非必要：start mqbroker.cmd -n 127.0.0.1:9876 -c E:\environment\rocketmq-all-5.2.0\conf\broker.conf |

- 技术简介：

| 技术                  | 说明      | 官网                                              |
|:--------------------|---------|:------------------------------------------------|
| springboot          | web框架   | https://spring.io/projects/spring-boot          |
| springcloud netflix | 分布式框架   | https://spring.io/projects/spring-cloud-netflix |
| apollo              | 配置中心    |                                                 |
| mybatis             | orm框架   | https://blog.mybatis.org/                       |
| mybatis-plus        | orm框架   | https://baomidou.com/                           |
| redis               | 缓存      | https://redis.io/                               |
| mongodb             | 缓存      | https://www.mongodb.com/zh-cn                   |
| caffeine            | 本地缓存    | https://github.com/ben-manes/caffeine           |
| jetcache            | 多级缓存框架  |                                                 |
| kafka               | 消息队列    | https://kafka.apache.org/                       |
| rocketmq            | 消息队列    | https://rocketmq.apache.org/                    |
| hutool              | 工具类     | https://hutool.cn/                              |
| sa-token            | 权限框架    | https://sa-token.cc/doc.html#/                  |
| oss                 | 对象存储    | https://www.aliyun.com/product/oss              |
| minio               | 对象存储    | https://min.io/                                 |
| mysql               | 数据库     | https://www.mysql.com/cn/                       |
| swagger             | api文档框架 | https://swagger.io/                             |
| docker              | 容器      | https://www.docker.com/                         |
| nginx               | 代理服务器   | https://nginx.org/cn/                           |
| elasticsearch       | 搜索引擎    |                                                 |
| filebeat            | 采集agent |                                                 |
| logstash            | 采集agent |                                                 |
| kibana              | 可视化分析工具 |                                                 |
| easy-rules          | 规则引擎    |                                                 |
| camunda             | 流程引擎    |                                                 |
| sharding-jdbc       | 分库分表    |                                                 |




# 架构设计总览
## 零、基础架构
1. 全局出入参
2. 全局响应体
3. 全局远程调用包装
4. 全局controller拦截设计
5. 全局dao拦截设计
6. 全局响应字段拦截设计

## 一、异常体系架构
```
设计目标
 - 分层管理异常（明确区分不同层的异常，避免异常混乱）
 - 统一返回格式（前后端对齐，提高可读性）
 - 可观测性（日志+监控，方便排查）
 - 业务错误码（错误码设计清晰，方便排查问题）
 - 可扩展性（新业务加入时可以方便扩展）
```

```
架构设计：
 - 异常层级划分（业务异常、系统异常、参数异常、外部异常、未知异常）
 - 异常传播与转换（业务异常直接返回、系统异常转换、外部异常兜底）
 - 错误码体系（统一错误码、分模块管理、支持国际化）
 - 监控与告警（记录日志、prometheus监控、异常告警）
 - 异常恢复与降级（缓存兜底、熔断、自动重试、幂等）
 - 分布式异常（feign&RPC统一处理、全链路追踪、事务补偿）
```

## 二、网关架构



## 三、权限体系架构

### 1、整体架构

> ```
> 角色：
>  - 客户端
>  - 网关
>  - 权限服务
>  - 其他服务
>  - 数据库
>  - 缓存中间件
> ```
>
> ```
> 整体架构描述
>  1. api-网关层
>   - 统一鉴权
>   
>  2. auth-权限服务
>   - 认证模块（登录、登出）
>   - RBAC模块（用户管理、角色管理、权限管理）
>   - 权限缓存服务（redis缓存策略、缓存更新监听）
>   
>  3. other-其他业务服务
>   - 仅关注本身业务逻辑
>   
>  4. infra-基础设施
>   - mysql（权限数据持久化）
>   - redis（会话缓存、权限缓存）
>   
> ```
>
> 

### 2、核心组件交互





## 四、日志体系架构
```
日志体系架构设计：
1. 日志分类	应用日志、请求日志、异常日志、审计日志
2. 日志采集	统一日志格式、日志切面 AOP、链路追踪（Sleuth）
3. 日志存储	分级存储（本地磁盘、Kafka、Elasticsearch）
4. 日志查询	Kibana + Elasticsearch，日志聚合分析
5. 日志分析	统计错误率、响应时间、用户行为
6. 日志告警	Prometheus + Loki 监控告警
7. 日志归档	冷热分层存储，长期归档
```



## 五、并发编程架构

```
1. 线程管理     线程池（ThreadPoolExecutor）、ForkJoinPool
2. 线程安全     volatile、synchronized、Lock、ThreadLocal
3. 线程通信     wait/notify、Condition、BlockingQueue
4. 并发工具类     CountDownLatch、CyclicBarrier、Semaphore
5. 任务调度     ScheduledThreadPoolExecutor、定时任务
6. 并发集合     ConcurrentHashMap、CopyOnWriteArrayList
7. 原子操作     AtomicInteger、LongAdder、CAS
8. 无锁并发     Disruptor、StampedLock
9. 并发设计模式     生产者-消费者、读写锁、线程封闭
10. 并发优化     任务拆分、锁分离、锁粗化、伪共享优化
11. 分布式锁     Redis 分布式锁、Zookeeper 分布式锁
12. 并发问题排查     jstack、jvisualvm、Arthas
```



## 六、多级缓存架构

```
多级缓存架构设计：
1. 缓存分层：L1 本地缓存（Caffeine），L2 分布式缓存（Redis），L3 持久化存储（DB）
2. 缓存淘汰策略：LRU（最近最少使用）、LFU（最少频率使用）、TTL 过期
3. 热点数据优化：本地缓存+布隆过滤器、预加载热点数据
4. 分布式缓存一致性：Cache-Aside、Write-Through、Write-Behind
5. 缓存穿透：布隆过滤器拦截无效请求
6. 缓存击穿：互斥锁 + 预热
7. 缓存雪崩：过期时间分散，限流熔断保护
```



## 七、统一调度架构

```
1. 任务调度模型：Quartz（单机）、XXL-JOB（分布式）、Kubernetes CronJob
2. 任务分片：Hash 取模、轮询分片
3. 任务高可用：任务幂等、任务重试、状态管理
4. 任务监控：任务执行日志、任务异常告警
5. 任务执行优化：并行执行、任务批处理、分布式锁
```



## 八、推荐搜索架构

```
1. 推荐算法	协同过滤、矩阵分解、深度学习
2. 召回策略	用户行为召回、内容召回、协同召回
3. 排序策略	机器学习排序（LTR）、规则排序
4. AB 测试	实验组 + 对照组，评估推荐效果
5. 数据管道	实时计算（Flink）、离线计算（Spark）
6. 画像构建	标签系统、特征工程、向量化存储
```



## 九、消息架构

```
1. 消息队列选型	Kafka（高吞吐）、RabbitMQ（低延迟）、RocketMQ（事务支持）
2. 消息可靠性	ACK 确认、幂等消费、死信队列
3. 消息顺序	单分区 FIFO、幂等消费
4. 消息积压处理	消费者限流 + 扩容
5. 事务消息	半消息 + 二阶段提交
6. 消息监控	Kafka UI、Prometheus 监控 MQ 状态
```



## 十、搜索架构

```
1. 搜索引擎选型：Elasticsearch
2. 分词优化：IK 分词、搜索分析器
3. 搜索权重调整：BM25、TF-IDF、自定义权重
4. 搜索缓存：本地缓存 + Redis 缓存 + ES 预热
5. 搜索索引优化：分片调整、冷热分层存储
6. 搜索性能优化：预计算、查询缓存、异步索引更新
```







