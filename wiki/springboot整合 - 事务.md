# 事务基础概念
1. 事务的四个特性：ACID（原子性、一致性、隔离性、持久性） 
2. 事务的隔离级别与传播行为
隔离级别：READ_UNCOMMITTED、READ_COMMITTED、REPEATABLE_READ、SERIALIZABLE
传播行为：REQUIRED、REQUIRES_NEW、NESTED 等 

# Spring Boot 中的事务管理基础
1.  Spring Boot 的事务支持：声明式事务与编程式事务
2. @Transactional 注解的使用
基本用法：事务回滚、提交、方法级别配置
回滚规则：哪些异常触发回滚，哪些异常不触发回滚
事务传播行为与隔离级别的配置

# Spring Boot 事务的回滚与异常处理
1. 异常处理与事务回滚策略
2. @Transactional 的回滚规则（回滚的异常类型）
3. 自定义回滚规则
4. 编程式事务管理：通过 TransactionTemplate 和 PlatformTransactionManager 实现事务管理

# Spring Boot 的多数据源事务管理
1. 配置和使用多数据源
2. 分布式事务与数据源切换
3. 配置多数据源的事务管理器（AbstractRoutingDataSource）

# 事务的嵌套与传播行为
1. 嵌套事务的应用场景与实现
2. 事务的传播行为：如何处理方法间的事务传播（例如：REQUIRES_NEW 和 NESTED）
3. 事务提交与回滚的顺序

# 事务与锁管理
1. 数据库事务中的锁机制（悲观锁与乐观锁）
2. Spring Boot 中的锁实现与事务结合使用
3. @Lock 注解与 @Transactional 配合
4. 自定义锁管理（例如：基于 Redis 的分布式锁） 

# Spring Boot 与分布式事务
7.1 基本分布式事务概念
7.2 Spring Boot 中的分布式事务解决方案
基于消息队列的事务（使用 Kafka 或 RabbitMQ）
使用 @Transactional 配合消息队列确保数据一致性
7.3 分布式事务框架：Seata、Atomikos 等
7.4 Saga 模式与 TCC 模式的实现与应用场景
8. 事务性能优化
8.1 优化事务的执行时间与锁粒度
8.2 使用事务缓存与合并事务
8.3 死锁分析与避免策略
8.4 提高事务的并发性与吞吐量
9. Spring Boot 事务实战
9.1 订单管理系统中的事务设计
9.2 账户转账中的事务管理与一致性
9.3 批量操作中的事务设计与优化