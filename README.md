# back-end-scaffolding
# 概述
脚手架->wiki(kakarot)+im(xiaoxingbomei)+gpt()


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

| 技术                  | 说明           | 官网                                            |
|:--------------------| -------------- |:------------------------------------------------|
| springboot          | web框架        | https://spring.io/projects/spring-boot          |
| springcloud netflix | 分布式框架     | https://spring.io/projects/spring-cloud-netflix |
| mybatis             | orm框架        | https://blog.mybatis.org/                       |
| mybatis-plus        | orm框架        | https://baomidou.com/                           |
| redis               | 缓存           | https://redis.io/                               |
| mongodb             | 缓存           | https://www.mongodb.com/zh-cn                   |
| caffeine            | 本地缓存       | https://github.com/ben-manes/caffeine           |
| kafka               | 消息队列       | https://kafka.apache.org/                       |
| rocketmq            | 消息队列       | https://rocketmq.apache.org/                       |
| hutool              | 工具类         | https://hutool.cn/                              |
| sa-token            | 权限框架       | https://sa-token.cc/doc.html#/                  |
| oss                 | 对象存储       | https://www.aliyun.com/product/oss              |
| minio               | 自建对象存储   | https://min.io/                                 |
| mysql               | 数据库         | https://www.mysql.com/cn/                       |
| swagger             | api文档框架    | https://swagger.io/                             |
| docker              | 容器           | https://www.docker.com/                         |
| nginx               | 代理服务器     | https://nginx.org/cn/                           |
| elasticsearch       | 搜索引擎       |                                                 |
| filebeat            | 采集agent      |                                                 |
| logstash            | 采集agent      |                                                 |
| kibanna             | 可视化分析工具 |                                                 |
|                     |                |                                                 |




# 架构设计
## 零、基础架构

## 一、异常体系架构

## 二、权限体系架构

## 三、日志体系架构

## 四、网关架构

## 五、熔断&限流&降级体系架构

## 六、线程池架构

## 七、多级缓存架构

## 八、统一调度架构

## 九、推荐搜索架构

## 十、消息队列架构

## 十一、搜索引擎架构

## 十二、IM服务架构

