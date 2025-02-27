package org.xiaoxingbomei.constant;

/**
 * 全部Api常量
 * 1、通用：系统
 * 2、通用：用户
 * 3、通用：机构
 * 4、通用：角色
 * 5、通用：权限
 * 6、通用：日志
 * 7、业务-大红书（小红书）
 * 8、业务-悬赏金系统
 * 8、业务-知识库（语雀）
 * 9、业务：IM（微信）
 * 10、业务：gpt（chatgpt）
 * 11、业务：广告
 * 12：业务：商城（淘宝）
 * 13：业务：搜索推荐系统（大众点评）
 * ##：《学习》
 */
public class ApiConstant
{

    /**
     * 通用：系统
     */
    public class System
    {
        public static final String getSystemInfo           = "/system/getSystemInfo";              // 系统信息搜索-（通用）
        public static final String getSystemInfoByMysql    = "/system/getSystemInfoByMysql";       // 系统信息搜索-通过数据库
        public static final String getSystemInfoByEs       = "/system/getSystemInfoByEs";          // 系统信息搜索-通过数据库
        public static final String createSystem            = "/system/createSystem";               // 创建系统（落库+同步es）
        public static final String updateSystem            = "/system/updateSystem";               // 更新系统（落库+同步es）
        public static final String deleteSystem            = "/system/deleteSystem";               // 删除系统（落库+同步es）
        public static final String multiHandleSystem       = "/system/multiHandleSystem";          // 批量操作系统信息（无则新增，有则修改）
    }

    /**
     * 配置
     */
    public class Config
    {
        public static final String getApolloConfig          = "/config/getApolloConfig";        // 获取Apollo配置信息
        public static final String getApolloValueByKey      = "/config/getApolloValueByKey";    // 获取Apollo配置信息，通过key获取value

        public static final String getNacosConfig           = "/config/getNacosConfig";         // 获取Nacos配置信息

        public static final String getMybatisConfig         = "/config/getMybatisConfig";       // 获取Mybatis配置信息

        public static final String getOpenFeignConfig       = "/config/getOpenFeignConfig";     // 获取OpenFeign配置信息

        public static final String getRedisConfig           = "/config/getRedisConfig";         // 获取Redis配置信息

        public static final String getSaTokenConfig         = "/config/getSaTokenConfig";       // 获取Sa-token配置信息

        public static final String getElasticSearchConfig   = "/config/getElasticSearchConfig"; // 获取es配置信息

        public static final String getXxlJobConfig          = "/config/getXxlJobConfig";        // 获取xxl-job配置信息

        public static final String getCamundaConfig         = "/config/getCamundaConfig";       // 获取camunda配置信息
    }

    /**
     * 用户
     */
    public class User
    {
        public static final String getAllUserInfo           = "/user/getAllUserInfo";           // 获取全部用户信息
        public static final String searchUserInfo           = "/user/searchUserInfo";           // 查询用户信息（数据库+redis+es）
        public static final String searchUserInfoById       = "/user/searchUserInfoById";       // 查询用户信息（精确查询）
        public static final String exportUserInfo           = "/user/exportUserInfo";           // 导出全部用户信息到excel
        public static final String exportUserInfoTemplate   = "/user/exportUserInfoTemplate";   // 获取用户excel模板
        public static final String updateUserInfoByTemplate = "/user/updateUserInfoByTemplate"; // 通过excel导入批量更新用户信息，无则新增，有则更新

        public static final String createUserIndex           = "/user/createUserIndex";         // 构建用户索引（es）
    }

    /**
     * 机构
     */
    public class Org
    {
        public static final String getOrgInfo               = "/org/getOrgInfo";             //
        public static final String getOrgInfoByOrgId        = "/org/getOrgInfoByOrgId";      //
        public static final String getSupOrgInfo            = "/org/getSupOrgInfo";          //
        public static final String getSubOrgInfo            = "/org/getSubOrgInfo";          //
    }

    /**
     * 角色
     */
    public class Role
    {
        public static final String getRoleInfo              = "/role/getRoleInfo";           // 获取角色信息
    }


    /**
     * 权限
     */
    public class Auth
    {

        public static final String getAuthInfo              = "/auth/getAuthInfo";

        public static final String generateCaptcha          = "/auth/generateCaptcha";      // 获取验证吗
        public static final String validateCaptcha          = "/auth/validateCaptcha";      // 校验验证吗


        public static final String doLogin                  = "/auth/doLogin";              // 登录
        public static final String isLogin                  = "/auth/isLogin";              // 是否登录，true/false
        public static final String checkLogin               = "/auth/checkLogin";           // 检查登录，抛出异常
        public static final String tokenInfo                = "/auth/tokenInfo";            // token信息
        public static final String logOut                   = "/auth/logOut";               // 注销

        public static final String getPermissionList        = "/auth/getPermissionList";    // 获取当前账号拥有的权限集合
        public static final String hasPermissionList        = "/auth/hasPermissionList";    // 当前账号是否拥有指定权限，true/false
        public static final String checkPermissionList      = "/auth/checkPermissionList";  // 当前账号是否含有指定权限标识，抛出异常
        public static final String checkPermissionAnd       = "/auth/checkPermissionAnd";   // 当前账号是否含有指定权限（指定多个，必须全部通过）
        public static final String CheckPermissionOr        = "/auth/CheckPermissionOr";    // 当前账号是否含有指定权限（指定多个，通过一个即可）

        public static final String getRoleList              = "/auth/getRoleList";          // 获取当前账号拥有的角色集合
        public static final String hasRole                  = "/auth/hasRole";              // 当前账号是否拥有指定角色，true/false
        public static final String checkRole                = "/auth/checkRole";            // 当前账号是否含有指定角色标识，如果验证未通过，则抛出异常
        public static final String checkRoleAnd             = "/auth/checkRoleAnd";         // 当前账号是否含有指定角色（指定多个，必须全部通过）
        public static final String checkRoleOr              = "/auth/checkRoleOr";          // 当前账号是否含有指定角色（指定多个，通过一个即可）
    }


    /**
     * 日志
     */
    public class Log
    {
        public static final String getLogInfo               = "/log/getLogInfo";                    // 获取当前工程所有的日志简介

        public static final String insertBusinessLogCommon  = "/log/insertBusinessLogCommon";       // 更新通用业务日志表

        public static final String insertSystemLogCommon    = "/log/insertSystemLogCommon";         // 更新通用业务日志表
    }


    /**
     * 业务：大红书
     * 1.
     * 2.
     * 3.
     */
    public class BigRedBook
    {
        public static final String Xxx1         = "/redbook/Xxx1";
        public static final String Xxx2         = "/redbook/Xxx2";
        public static final String Xxx3         = "/redbook/Xxx3";
    }

    /**
     * 业务：悬赏金系统
     *
     * 介绍：
     * HungerGamesSystem 是一个基于悬赏机制的任务管理平台，致力于通过任务认领与执行、悬赏金激励等方式，鼓励个人主动承担责任和参与合作。
     * 用户可以通过发布悬赏金任务，激励他人帮助解决问题；同时，用户也可以根据任务的悬赏金额认领并完成任务，获得相应的奖励。
     * 系统以分布式微服务架构为基础，确保高效、可扩展且稳定的运行。
     *
     * 愿景：
     * HungerGamesSystem 希望打破传统的管理模式，让每个成员都能根据兴趣与能力主动认领任务，并通过悬赏金机制促进团队协作与个人成长。
     * 我们致力于构建一个透明、激励公平、充满活力的任务平台，在其中每个人都能找到价值，创造价值，实现自我发展。
     *
     * 业务&功能架构：
     * 1. 用户服务：用户的信息管理、注册、登录、认证等（沿用系统本身）
     * 2. 悬赏金服务：悬赏金管理
     * 3. 任务服务：任务管理
     * 4. 交易服务：悬赏金兑换、处理支付、奖励方法等财务交易相关
     * 5. 通知服务：业务通知、任务进度提醒、奖励发放等通知
     */


    /**
     * 业务：知识库（仿语雀）
     * 1.
     * 2.
     * 3.
     */
    public class Wiki
    {
        public static final String mine_knowledgeBases_search       = "/wiki/mine/knowledgeBases/search";     // 个人知识库列表查询
        public static final String mine_knowledgeBase_search        = "/wiki/mine/knowledgeBase/search";      // 个人知识库列表查询
        public static final String mine_knowledgeBase_create        = "/wiki/mine/knowledgeBase/create";      // 个人知识库创建
        public static final String mine_knowledgeBase_multiCreate   = "/wiki/mine/knowledgeBase/multiCreate"; // 个人知识库批量创建
    }

    /**
     * 业务：IM
     * 1.
     * 2.
     * 3.
     */
    public class Chat
    {
        public static final String search = "/chat/search";
        public static final String create = "/chat/create";
    }

    /**
     * 业务：Gpt
     */
    public class Gpt
    {
        public static final String Xxx1 = "/gpt/Xxx1"; //
        public static final String Xxx2 = "/gpt/Xxx2"; //
        public static final String Xxx3 = "/gpt/Xxx3"; //
    }

    /**
     * 业务：广告
     */
    public class Advertising
    {
        public static final String Xxx1 = "/ad/Xxx1"; //
        public static final String Xxx2 = "/ad/Xxx2"; //
        public static final String Xxx3 = "/ad/Xxx3"; //
    }


    /**
     * 业务：商城
     */
    public class Mall
    {
        public static final String Xxx1 = "/mall/Xxx1"; //
        public static final String Xxx2 = "/mall/Xxx2"; //
        public static final String Xxx3 = "/mall/Xxx3"; //
    }


    /**
     * 业务：数据资产-数据标准
     */
    public class DataAssetStd
    {
        public static final String Xxx1 = "/std/Xxx1"; //
        public static final String Xxx2 = "/std/Xxx2"; //
        public static final String Xxx3 = "/std/Xxx3"; //
    }

    /**
     * 业务：数据资产-数据质量
     */
    public class DataAssetQlty
    {
        public static final String Xxx1 = "/qlty/Xxx1"; //
        public static final String Xxx2 = "/qlty/Xxx2"; //
        public static final String Xxx3 = "/qlty/Xxx3"; //
    }



    /**
     * 持续不断的学习
     */
    public class Study
    {
        public static final String getTechLearningInfo                  = "/study/getTechLearningInfo";          // 获取技术学习信息

        public static final String java_io_Xxx                          = "/study/java_io_Xxx";     // java-io-

        public static final String java_stream_Xxx1                     = "/study/java_stream_Xxx"; // java-stream-
        public static final String java_stream_Xxx2                     = "/study/java_stream_Xxx"; // java-stream-

        public static final String java_multi_createThreadByThread      = "/study/java_multi_createThreadByThread";     // java-并发-创建线程（thread）
        public static final String java_multi_createThreadByRunnable    = "/study/java_multi_createThreadByRunnable";   // java-并发-创建线程（runnable）
        public static final String java_multi_createThreadByCallable    = "/study/java_multi_createThreadByCallable";   // java-并发-创建线程（callable）
        public static final String java_multi_createThreadByThreadPool  = "/study/java_multi_createThreadByThreadPool"; // java-并发-创建线程（线程池）
        public static final String java_multi_threadStateNew            = "/study/java_multi_threadStateNew";           // java-并发-线程状态-new
        public static final String java_multi_threadStateRunnable       = "/study/java_multi_threadStateRunnable";      // java-并发-线程状态-runnable
        public static final String java_multi_threadStateBlocked        = "/study/java_multi_threadStateBlocked";       // java-并发-线程状态-blocked
        public static final String java_multi_threadStateWaiting        = "/study/java_multi_threadStateWaiting";       // java-并发-线程状态-waiting
        public static final String java_multi_threadStateTimedWaiting   = "/study/java_multi_threadStateTimedWaiting";  // java-并发-线程状态-timedwaiting
        public static final String java_multi_threadStateTerminated     = "/study/java_multi_threadStateTerminated";    // java-并发-线程状态-terminated

        public static final String dynamicThreadPool_create             = "/study/dynamicThreadPool_create";     // 动态线程池-创建
        public static final String dynamicThreadPool_update             = "/study/dynamicThreadPool_update";     // 动态线程池-更新参数
        public static final String dynamicThreadPool_get                = "/study/dynamicThreadPool_get";        // 动态线程池-查询状态
        public static final String dynamicThreadPool_delete             = "/study/dynamicThreadPool_delete";     // 动态线程池-销毁
        public static final String dynamicThreadPool_createTask         = "/study/dynamicThreadPool_createTask"; // 动态线程池-创建任务

        public static final String java_Async_Xxx                       = "/study/java_Async_Xxx";               // java-异步-

        public static final String mongodb_Insert                       = "/study/mongodb_Insert";               // mongodb-创建
        public static final String mongodb_MultiInsert                  = "/study/mongodb_MultiInsert";          // mongodb-批量创建
        public static final String mongodb_Save                         = "/study/mongodb_Save";                 // mongodb-保存
        public static final String mongodb_Search                       = "/study/mongodb_Search";               // mongodb-查询
        public static final String mongodb_Update                       = "/study/mongodb_Update";               // mongodb-修改
        public static final String mongodb_Delete                       = "/study/mongodb_Delete";               // mongodb-删除

        public static final String fastJson_ObjectToJsonString          = "/study/fastJson_ObjectToJsonString";  // fastJson-对象转JSON字符串（序列化）
        public static final String fastJson_JsonStringToObject          = "/study/fastJson_JsonStringToObject";  // fastJson-JSON字符串转对象（反序列化）
        public static final String fastJson_PrettyPrint                 = "/study/fastJson_PrettyPrint";         // fastJson-格式化JSON输出
        public static final String fastJson_JSONStringToMap             = "/study/fastJson_JSONStringToMap";     // fastJson-JSON转为Map
        public static final String fastJson_JSONStringToList            = "/study/fastJson_JSONStringToList";    // fastJson-JSON转为List

        public static final String redis_expire                         = "/study/redis_expire";                 // redis-通用-设置过期时间
        public static final String redis_keys                           = "/study/redis_keys";                 // redis-通用-获取所有匹配的key
        public static final String redis_type                           = "/study/redis_type";                 // redis-通用-获取key的数据类型
        public static final String redis_rename                         = "/study/redis_rename";                 // redis-通用-重命名key
        public static final String redis_move                           = "/study/redis_move";                 // redis-通用-将key移动到其他数据库
        public static final String redis_randomKey                      = "/study/redis_randomKey";              // redis-通用-随机获取一个key
        public static final String redis_scan                           = "/study/redis_scan";                   // redis-通用-扫描key
        public static final String redis_ttl                            = "/study/redis_ttl";                    // redis-通用-获取key剩余过期时间

        public static final String redis_StringSet                        = "/study/redis_StringSet";              // redis-string-设置值
        public static final String redis_StringSetWithExpire              = "/study/redis_StringSetWithExpire";    // redis-string-设置值和过期时间
        public static final String redis_StringGet                        = "/study/redis_StringGet";              // redis-string-获取
        public static final String redis_StringDelete                     = "/study/redis_StringDelete";           // redis-string-删除
        public static final String redis_StringIncr                       = "/study/redis_StringIncr";             // redis-string-自增
        public static final String redis_StringDecr                       = "/study/redis_StringDecr";             // redis-string-自减

        public static final String redis_hashSave                         = "/study/redis_hashSave";               // redis-hash-保存
        public static final String redis_hashFind                         = "/study/redis_hashFind";               // redis-hash-查询hash中指定字段的值
        public static final String redis_hashFindAll                      = "/study/redis_hashFindAll";            // redis-hash-查询整个hash中所有字段和值
        public static final String redis_hashDelete                       = "/study/redis_hashDelete";             // redis-hash-删除hash中指定字段
        public static final String redis_hashDeleteAll                    = "/study/redis_hashDeleteAll";          // redis-hash-删除整个hash

        public static final String redis_listLeftPush                     = "/study/redis_listLeftPush";           // redis-list-左推入
        public static final String redis_listRightPush                    = "/study/redis_listRightPush";          // redis-list-右推入
        public static final String redis_listLeftPop                      = "/study/redis_listLeftPop";            // redis-list-左弹出
        public static final String redis_listRightPop                     = "/study/redis_listRightPop";           // redis-list-右弹出
        public static final String redis_listRange                        = "/study/redis_listRange";              // redis-list-获取列表中的指定范围的元素
        public static final String redis_listRemove                     = "/study/redis_listRemove";             // redis-list-删除列表中指定数量的某个值
        public static final String redis_listRemoveByIndex                = "/study/redis_listRemoveByIndex";        // redis-list-通过索引删除范围内元素
        public static final String redis_listRemoveByRange                = "/study/redis_listRemoveByRange";        // redis-list-通过范围反向删除元素
        public static final String redis_listSize                         = "/study/redis_listSize";               // redis-list-获取列表的长度
        public static final String redis_listSet                          = "/study/redis_listSet";                // redis-list-设置列表中指定索引的值

        public static final String redis_setAdd                           = "/study/redis_setAdd";                 // redis-set-添加元素
        public static final String redis_setAddAll                        = "/study/redis_setAddAll";              // redis-set-添加多个元素
        public static final String redis_setGetAll                        = "/study/redis_setGetAll";              // redis-set-获取所有成员
        public static final String redis_setGetRandom                     = "/study/redis_setGetRandom";           // redis-set-获取指定数量的随机成员
        public static final String redis_setIsMember                      = "/study/redis_setIsMember";            // redis-set-检查某个元素是否是成员
        public static final String redis_setRemove                        = "/study/redis_setRemove";              // redis-set-删除指定的元素
        public static final String redis_setSize                          = "/study/redis_setSize";                // redis-set-获取大小（成员数量）
        
        public static final String redis_zsetAdd                          = "/study/redis_zsetAdd";                  // redis-zset-添加元素
        public static final String redis_zsetGetByRange                   = "/study/redis_zsetGetByRange";           // redis-zset-获取 Redis ZSet 中指定区间的成员（按分数排序）
        public static final String redis_zsetGetByRangeReverse            = "/study/redis_zsetGetByRangeReverse";      // redis-zset-获取 Redis ZSet 中指定区间的成员（按分数倒序排序）
        public static final String redis_zsetGetByScoreRange              = "/study/redis_zsetGetByScoreRange";         // redis-zset-根据指定的分数范围获取 Redis ZSet 中的成员
        public static final String redis_zsetGetByScoreRangeReverse         = "/study/redis_zsetGetByScoreRangeReverse";    // redis-zset-根据指定的分数范围获取 Redis ZSet 中的成员（按分数倒序排序）
        public static final String redis_zsetSize                         = "/study/redis_zsetSize";                   // redis-zset-获取 Redis ZSet 的大小（成员数量）
        public static final String redis_zsetRemove                       = "/study/redis_zsetRemove";                 // redis-zset-从 Redis ZSet 中移除指定的元素
        public static final String redis_zsetRemoveByRange                  = "/study/redis_zsetRemoveByRange";            // redis-zset-根据成员的排名范围从 Redis ZSet 中移除元素
        public static final String redis_zsetRemoveByScoreRange             = "/study/redis_zsetRemoveByScoreRange";           // redis-zset-根据分数范围从 Redis ZSet 中移除元素
        public static final String redis_zsetGetScore                       = "/study/redis_zsetGetScore";                  // redis-zset-获取 Redis ZSet 中指定元素的分数
        public static final String redis_zsetIncrementScore                 = "/study/redis_zsetIncrementScore";              // redis-zset-增量增加 Redis ZSet 中指定元素的分数


        // Redis Bitmap 操作
        public static final String redis_bitSet                              = "/study/redis_bitSet";                                           // redis-bitmap-设置位图的某一位
        public static final String redis_bitGet                              = "/study/redis_bitGet";                                           // redis-bitmap-获取位图的某一位
        public static final String redis_bitCount                            = "/study/redis_bitCount";                                           // redis-bitmap-统计位图中1的数量
        public static final String redis_bitPos                              = "/study/redis_bitPos";                                           // redis-bitmap-查找位图中第一个0或1
        public static final String redis_bitOp                               = "/study/redis_bitOp";                                            // redis-bitmap-对多个位图进行位运算

        // Redis HyperLogLog 操作
        public static final String redis_pfAdd                                 = "/study/redis_pfAdd";                                              // redis-hyperloglog-添加元素
        public static final String redis_pfCount                               = "/study/redis_pfCount";                                              // redis-hyperloglog-获取基数估算值
        public static final String redis_pfMerge                               = "/study/redis_pfMerge";                                              // redis-hyperloglog-合并多个HyperLogLog

        // Redis GEO 操作
        public static final String redis_geoAdd                                = "/study/redis_geoAdd";                                               // redis-geo-添加地理位置
        public static final String redis_geoPos                                = "/study/redis_geoPos";                                               // redis-geo-获取地理位置
        public static final String redis_geoDist                               = "/study/redis_geoDist";                                                // redis-geo-计算两个位置之间的距离
        public static final String redis_geoRadius                               = "/study/redis_geoRadius";                                                // redis-geo-查找指定范围内的地理位置
        public static final String redis_geoHash                               = "/study/redis_geoHash";                                                // redis-geo-获取地理位置的geohash值

        // Redis Stream 操作
        public static final String redis_streamAdd                               = "/study/redis_streamAdd";                                                // redis-stream-添加消息
        public static final String redis_streamRead                                = "/study/redis_streamRead";                                                // redis-stream-读取消息
        public static final String redis_streamRange                               = "/study/redis_streamRange";                                                // redis-stream-获取消息范围
        public static final String redis_streamLen                                 = "/study/redis_streamLen";                                                // redis-stream-获取Stream长度
        public static final String redis_streamTrim                                = "/study/redis_streamTrim";                                                // redis-stream-裁剪Stream
        public static final String redis_streamGroups                                = "/study/redis_streamGroups";                                                // redis-stream-获取消费者组
        public static final String redis_streamCreateGroup                             = "/study/redis_streamCreateGroup";                                                // redis-stream-创建消费者组

        // Redis 事务操作
        public static final String redis_multi                                     = "/study/redis_multi";                                                // redis-事务-开启事务
        public static final String redis_exec                                      = "/study/redis_exec";                                                // redis-事务-执行事务
        public static final String redis_discard                                   = "/study/redis_discard";                                                // redis-事务-丢弃事务
        public static final String redis_watch                                     = "/study/redis_watch";                                                // redis-事务-监视key
        public static final String redis_unwatch                                     = "/study/redis_unwatch";                                                // redis-事务-取消监视

        // Redis 发布订阅操作
        public static final String redis_publish                                     = "/study/redis_publish";                                                // redis-发布订阅-发布消息
        public static final String redis_subscribe                                     = "/study/redis_subscribe";                                                // redis-发布订阅-订阅频道
        public static final String redis_psubscribe                                     = "/study/redis_psubscribe";                                                // redis-发布订阅-模式订阅
        public static final String redis_pubsubChannels                                     = "/study/redis_pubsubChannels";                                                // redis-发布订阅-查看活跃频道
        public static final String redis_pubsubNumsub                                     = "/study/redis_pubsubNumsub";


        public static final String cookie_create                          = "/study/cookie_create";                    // cookie-创建
        public static final String cookie_update                          = "/study/cookie_update";                    // cookie-修改
        public static final String cookie_search                          = "/study/cookie_search";                    // cookie-查询
        public static final String cookie_delete                          = "/study/cookie_delete";                    // cookie-删除

        public static final String session_create                         = "/study/session_create";                     // session-创建
        public static final String session_update                         = "/study/session_update";                     // session-修改
        public static final String session_search                         = "/study/session_search";                     // session-查询
        public static final String session_delete                         = "/study/session_delete";                     // session-删除

        // 索引操作相关接口
        public static final String elasticsearch_createIndex              = "/study/elasticsearch_createIndex";             // elasticsearch-创建索引
        public static final String elasticsearch_deleteIndex              = "/study/elasticsearch_deleteIndex";             // elasticsearch-删除索引
        public static final String elasticsearch_existsIndex              = "/study/elasticsearch_existsIndex";             // elasticsearch-检查索引是否存在
        public static final String elasticsearch_getIndex                 = "/study/elasticsearch_getIndex";                // elasticsearch-获取索引的元数据
        public static final String elasticsearch_createIndexMapping         = "/study/elasticsearch_createIndexMapping";        // elasticsearch-创建索引映射
        public static final String elasticsearch_getIndexMapping            = "/study/elasticsearch_getIndexMapping";           // elasticsearch-获取索引映射
        public static final String elasticsearch_updateIndexMapping         = "/study/elasticsearch_updateIndexMapping";          // elasticsearch-更新索引映射
        public static final String elasticsearch_addIndexAlias              = "/study/elasticsearch_addIndexAlias";               // elasticsearch-给索引添加别名
        public static final String elasticsearch_removeIndexAlias           = "/study/elasticsearch_removeIndexAlias";            // elasticsearch-从索引移除别名
        public static final String elasticsearch_refreshIndex               = "/study/elasticsearch_refreshIndex";                // elasticsearch-刷新索引
        public static final String elasticsearch_flushIndex                 = "/study/elasticsearch_flushIndex";                  // elasticsearch-刷新索引缓存
        public static final String elasticsearch_updateByQuery              = "/study/elasticsearch_updateByQuery";                 // elasticsearch-根据查询更新文档
        public static final String elasticsearch_deleteByQuery              = "/study/elasticsearch_deleteByQuery";                 // elasticsearch-根据查询删除文档
        public static final String elasticsearch_reindex                    = "/study/elasticsearch_reindex";                       // elasticsearch-索引重建
        public static final String elasticsearch_rolloverIndex              = "/study/elasticsearch_rolloverIndex";                     // elasticsearch-索引翻滚
        public static final String elasticsearch_getIndexSettings           = "/study/elasticsearch_getIndexSettings";                  // elasticsearch-获取索引设置
        public static final String elasticsearch_updateIndexSettings          = "/study/elasticsearch_updateIndexSettings";                 // elasticsearch-更新索引设置
        public static final String elasticsearch_getIndicesByAlias            = "/study/elasticsearch_getIndicesByAlias";                 // elasticsearch-获取某个别名下的所有索引
        public static final String elasticsearch_closeIndex                 = "/study/elasticsearch_closeIndex";                          // elasticsearch-关闭索引
        public static final String elasticsearch_openIndex                  = "/study/elasticsearch_openIndex";                             // elasticsearch-打开索引
        public static final String elasticsearch_segment                    = "/study/elasticsearch_segmentXxx";                              // elasticsearch-段-xxx

        // 文档操作
        public static final String elasticsearch_indexDocument              = "/study/elasticsearch_indexDocument";                         // elasticsearch-插入文档
        public static final String elasticsearch_getDocument                = "/study/elasticsearch_getDocument";                             // elasticsearch-获取文档
        public static final String elasticsearch_existsDocument             = "/study/elasticsearch_existsDocument";                          // elasticsearch-检查文档是否存在
        public static final String elasticsearch_updateDocument             = "/study/elasticsearch_updateDocument";                              // elasticsearch-更新文档
        public static final String elasticsearch_deleteDocument             = "/study/elasticsearch_deleteDocument";                              // elasticsearch-删除文档
        public static final String elasticsearch_bulkInsert                 = "/study/elasticsearch_bulkInsert";                                    // elasticsearch-批量操作文档

        // 搜索操作
        public static final String elasticsearch_search                     = "/study/elasticsearch_search";                                      // elasticsearch-查询文档
        public static final String elasticsearch_searchById                 = "/study/elasticsearch_searchById";                                      // elasticsearch-根据ID查询文档
        public static final String elasticsearch_searchByField              = "/study/elasticsearch_searchByField";                                     // elasticsearch-根据字段查询文档
        public static final String elasticsearch_searchAggregations           = "/study/elasticsearch_searchAggregations";                                    // elasticsearch-聚合查询

        // 批量操作
        public static final String elasticsearch_bulkIndex                  = "/study/elasticsearch_bulkIndex";                                         // elasticsearch-批量插入文档
        public static final String elasticsearch_bulkUpdate                 = "/study/elasticsearch_bulkUpdate";                                          // elasticsearch-批量更新文档

        // 集群和节点操作相关接口
        public static final String elasticsearch_clusterHealth              = "/study/elasticsearch_clusterHealth";                                         // elasticsearch-获取集群健康状态
        public static final String elasticsearch_clusterStats                 = "/study/elasticsearch_clusterStats";                                          // elasticsearch-获取集群统计信息
        public static final String elasticsearch_nodeInfo                     = "/study/elasticsearch_nodeInfo";                                              // elasticsearch-获取节点信息

        // 快照操作相关接口
        public static final String elasticsearch_createSnapshot               = "/study/elasticsearch_createSnapshot";                                          // elasticsearch-创建快照
        public static final String elasticsearch_restoreSnapshot              = "/study/elasticsearch_restoreSnapshot";                                         // elasticsearch-恢复快照
        public static final String elasticsearch_deleteSnapshot               = "/study/elasticsearch_deleteSnapshot";                                          // elasticsearch-删除快照
        public static final String elasticsearch_getSnapshot                  = "/study/elasticsearch_getSnapshot";                                             // elasticsearch-获取快照信息

        // 额外的接口
        public static final String elasticsearch_explainSearch                = "/study/elasticsearch_explainSearch";                                             // elasticsearch-解释查询结果
        public static final String elasticsearch_searchWithHighlighting         = "/study/elasticsearch_searchWithHighlighting";                                          // elasticsearch-带有高亮的查询
        public static final String elasticsearch_getMappingByField              = "/study/elasticsearch_getMappingByField";                                             // elasticsearch-获取特定字段的映射
        public static final String elasticsearch_multiTermSearch                = "/study/elasticsearch_multiTermSearch";                                             // elasticsearch-多字段查询
        public static final String elasticsearch_getDocumentSource              = "/study/elasticsearch_getDocumentSource";                                             // elasticsearch-获取文档源数据
        public static final String elasticsearch_termVector                   = "/study/elasticsearch_termVector";                                              // elasticsearch-获取文档的词项向量


        public static final String xxljob_Xxx                             = "/study/xxljob_Xxx";                                         // xxljob-

        public static final String camunda_Xxx                            = "/study/camunda_Xxx";                                        // camunda-

        public static final String mybatis_Xxx                             = "/study/mybatis_Xxx";                                         // mybatis-

        public static final String mybatisplus_Xxx                         = "/study/mybatisplus_Xxx";                                       // mybatisplus-

        public static final String feign_Xxx                               = "/study/feign_Xxx";                                           // feign-

        public static final String mapstruct_Xxx                             = "/study/mapstruct_xx";                                         // mapstruct-

        public static final String netty_Xxx                               = "/study/netty_Xxx";                                           // netty-

        public static final String kafka_Product                           = "/study/kafka_Product";                                         // kafka-生产一条消息
        public static final String kafka_Consume                           = "/study/kafka_Consume";                                         // kafka-消费一条消息

        public static final String rocketMq_product                          = "/study/rocketMq_product";                                       // rocketMq-生产一条消息
        public static final String rocketMq_consume                          = "/study/rocketMq_consume";                                       // rocketMq-生产一条消息

        public static final String minio_createBucket                        = "/study/minio_createBucket";                                         // minio-创建bucket
        public static final String minio_searchBucket                        = "/study/minio_searchBucket";                                         // minio-列出bucket和对象
        public static final String minio_deleteBucket                        = "/study/minio_deleteBucket";                                         // minio-删除bucket
        public static final String minio_uploadFile                          = "/study/minio_uploadFile";                                           // minio-上传文件
        public static final String minio_downloadFile                        = "/study/minio_downloadFile";                                           // minio-下载文件
        public static final String minio_deleteFile                          = "/study/minio_deleteFile";                                           // minio-删除文件

        public static final String fastexcel_downloadExcel                   = "/study/fastexcel_downloadExcel";                                         // fastexcel-下载模板
        public static final String fastexcel_uploadExcel                       = "/study/fastexcel_uploadExcel";                                           // fastexcel-上传文件 读取excel中日志并落库
        public static final String fastexcel_ReadExcel                         = "/study/fastexcel_ReadExcel";                                             // fastexcel-读取excel文件
        public static final String fastexcel_CreateExcel                       = "/study/fastexcel_CreateExcel";                                           // fastexcel-创建excel文件

        public static final String showProgress                              = "/study/showProgress";

                                              // redis-发布订阅-查看订阅数
    }


    /**
     * 算法
     */
    public class LeetCode
    {
        public static final String easy_1       = "/leetCode/easy_1";   // 两数之和
        public static final String easy_136     = "/leetCode/easy_136"; // 只出现一次的数字
        public static final String easy_206     = "/leetCode/easy_206"; // 反转链表

        public static final String normal_1     = "/leetCode/normal_1"; //

        public static final String hard_1       = "/leetCode/hard_1"; //
    }


}
