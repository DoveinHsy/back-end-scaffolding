package org.xiaoxingbomei.service;

import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.GlobalEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TechService
{

    GlobalEntity java_multi_createThreadByThread    (String paramString);
    GlobalEntity java_multi_createThreadByRunnable  (String paramString);
    GlobalEntity java_multi_createThreadByCallable  (String paramString);
    GlobalEntity java_multi_createThreadByThreadPool(String paramString);

    GlobalEntity java_multi_threadStateNew         (String paramString);
    GlobalEntity java_multi_threadStateRunnable    (String paramString);
    GlobalEntity java_multi_threadStateBlocked     (String paramString);
    GlobalEntity java_multi_threadStateWaiting     (String paramString);
    GlobalEntity java_multi_threadStateTimedWaiting(String paramString);
    GlobalEntity java_multi_threadStateTerminated  (String paramString);

    GlobalEntity dynamicThreadPool_create(String paramString);
    GlobalEntity dynamicThreadPool_update(String paramString);
    GlobalEntity dynamicThreadPool_get   (String paramString);
    GlobalEntity dynamicThreadPool_delete(String paramString);




    GlobalEntity mongodb_Insert     (String paramString);
    GlobalEntity mongodb_MultiInsert(String paramString);
    GlobalEntity mongodb_Save       (String paramString);
    GlobalEntity mongodb_Search     (String paramString);
    GlobalEntity mongodb_Update     (String paramString);
    GlobalEntity mongodb_Delete     (String paramString);

    GlobalEntity fastJson_ObjectToJsonString(String paramString);
    GlobalEntity fastJson_JsonStringToObject(String paramString);
    GlobalEntity fastJson_PrettyPrint       (String paramString);
    GlobalEntity fastJson_JSONStringToMap   (String paramString);
    GlobalEntity fastJson_JSONStringToList  (String paramString);


    GlobalEntity redis_expire       (String paramString);

    GlobalEntity redis_StringSet          (String paramString);
    GlobalEntity redis_StringSetWithExpire(String paramString);
    GlobalEntity redis_StringGet          (String paramString);
    GlobalEntity redis_StringDelete       (String paramString);
    GlobalEntity redis_StringIncr         (String paramString);
    GlobalEntity redis_StringDecr         (String paramString);

    GlobalEntity redis_hashSave     (String paramString);
    GlobalEntity redis_hashFind     (String paramString);
    GlobalEntity redis_hashFindAll  (String paramString);
    GlobalEntity redis_hashDelete   (String paramString);
    GlobalEntity redis_hashDeleteAll(String paramString);

    GlobalEntity redis_listLeftPush (String paramString);
    GlobalEntity redis_listRightPush(String paramString);
    GlobalEntity redis_listLeftPop  (String paramString);
    GlobalEntity redis_listRightPop (String paramString);
    GlobalEntity redis_listRange    (String paramString);
    GlobalEntity redis_listRemove   (String paramString);
    GlobalEntity redis_listRemoveByIndex   (String paramString);
    GlobalEntity redis_listRemoveByRange   (String paramString);
    GlobalEntity redis_listSize     (String paramString);
    GlobalEntity redis_listSet      (String paramString);

    GlobalEntity redis_setAdd      (String paramString);
    GlobalEntity redis_setAddAll   (String paramString);
    GlobalEntity redis_setGetAll   (String paramString);
    GlobalEntity redis_setGetRandom(String paramString);
    GlobalEntity redis_setIsMember (String paramString);
    GlobalEntity redis_setRemove   (String paramString);
    GlobalEntity redis_setSize     (String paramString);

    GlobalEntity redis_zsetAdd                   (String paramString);
    GlobalEntity redis_zsetGetByRange            (String paramString);
    GlobalEntity redis_zsetGetByRangeReverse     (String paramString);
    GlobalEntity redis_zsetGetByScoreRange       (String paramString);
    GlobalEntity redis_zsetGetByScoreRangeReverse(String paramString);
    GlobalEntity redis_zsetSize                  (String paramString);
    GlobalEntity redis_zsetRemove                (String paramString);
    GlobalEntity redis_zsetRemoveByRange         (String paramString);
    GlobalEntity redis_zsetRemoveByScoreRange    (String paramString);
    GlobalEntity redis_zsetGetScore              (String paramString);
    GlobalEntity redis_zsetIncrementScore        (String paramString);

    // Redis Key 通用操作
    GlobalEntity redis_keys(String paramString);              // 获取所有匹配的key
    GlobalEntity redis_type(String paramString);             // 获取key的数据类型
    GlobalEntity redis_rename(String paramString);           // 重命名key
    GlobalEntity redis_move(String paramString);             // 将key移动到其他数据库
    GlobalEntity redis_randomKey(String paramString);        // 随机获取一个key
    GlobalEntity redis_scan(String paramString);             // 扫描key
    GlobalEntity redis_ttl(String paramString);              // 获取key剩余过期时间

    // Redis Bitmap 操作
    GlobalEntity redis_bitSet(String paramString);              // 设置位图的某一位
    GlobalEntity redis_bitGet(String paramString);              // 获取位图的某一位
    GlobalEntity redis_bitCount(String paramString);            // 统计位图中1的数量
    GlobalEntity redis_bitPos(String paramString);              // 查找位图中第一个0或1
    GlobalEntity redis_bitOp(String paramString);               // 对多个位图进行位运算

    // Redis HyperLogLog 操作
    GlobalEntity redis_pfAdd(String paramString);               // 添加元素
    GlobalEntity redis_pfCount(String paramString);             // 获取基数估算值
    GlobalEntity redis_pfMerge(String paramString);             // 合并多个HyperLogLog

    // Redis GEO 操作
    GlobalEntity redis_geoAdd(String paramString);              // 添加地理位置
    GlobalEntity redis_geoPos(String paramString);              // 获取地理位置
    GlobalEntity redis_geoDist(String paramString);             // 计算两个位置之间的距离
    GlobalEntity redis_geoRadius(String paramString);           // 查找指定范围内的地理位置
    GlobalEntity redis_geoHash(String paramString);             // 获取地理位置的geohash值

    // Redis Stream 操作
    GlobalEntity redis_streamAdd(String paramString);           // 添加消息
    GlobalEntity redis_streamRead(String paramString);          // 读取消息
    GlobalEntity redis_streamRange(String paramString);         // 获取消息范围
    GlobalEntity redis_streamLen(String paramString);           // 获取Stream长度
    GlobalEntity redis_streamTrim(String paramString);          // 裁剪Stream
    GlobalEntity redis_streamGroups(String paramString);        // 获取消费者组
    GlobalEntity redis_streamCreateGroup(String paramString);   // 创建消费者组

    // Redis 事务操作
    GlobalEntity redis_multi(String paramString);               // 开启事务
    GlobalEntity redis_exec(String paramString);                // 执行事务
    GlobalEntity redis_discard(String paramString);             // 丢弃事务
    GlobalEntity redis_watch(String paramString);               // 监视key
    GlobalEntity redis_unwatch(String paramString);             // 取消监视

    // Redis 发布订阅操作
    GlobalEntity redis_publish(String paramString);             // 发布消息
    GlobalEntity redis_subscribe(String paramString);           // 订阅频道
    GlobalEntity redis_psubscribe(String paramString);          // 模式订阅
    GlobalEntity redis_pubsubChannels(String paramString);      // 查看活跃频道
    GlobalEntity redis_pubsubNumsub(String paramString);        // 查看订阅数


    GlobalEntity cookie_create(String paramString, HttpServletResponse response);
    GlobalEntity cookie_update(String paramString);
    GlobalEntity cookie_search(String paramString,HttpServletRequest request);
    GlobalEntity cookie_delete(String paramString,HttpServletRequest request,HttpServletResponse response);

    GlobalEntity session_create(String paramString, HttpServletRequest request);
    GlobalEntity session_update(String paramString, HttpServletRequest request);
    GlobalEntity session_search(String paramString, HttpServletRequest request);
    GlobalEntity session_delete(String paramString, HttpServletRequest request);

    GlobalEntity elasticsearch_createIndex(String paramString);
    GlobalEntity elasticsearch_deleteIndex(String paramString);
    GlobalEntity elasticsearch_existsIndex(String paramString);
    GlobalEntity elasticsearch_getIndex   (String paramString);

    GlobalEntity elasticsearch_indexDocument(String paramString);
    GlobalEntity elasticsearch_getDocument(String paramString);
    GlobalEntity elasticsearch_existsDocument(String paramString);
    GlobalEntity elasticsearch_updateDocument(String paramString);
    GlobalEntity elasticsearch_deleteDocument(String paramString);
    GlobalEntity elasticsearch_bulkInsert(String paramString);


    GlobalEntity kafka_Product(String paramString);
    GlobalEntity kafka_Consume(String paramString);

    GlobalEntity minio_createBucket(String paramString);
    GlobalEntity minio_searchBucket(String paramString);
    GlobalEntity minio_deleteBucket(String paramString);
    GlobalEntity minio_uploadFile  (String paramString);
    GlobalEntity minio_downloadFile(String paramString);
    GlobalEntity minio_deleteFile  (String paramString);

    GlobalEntity fastexcel_downloadExcel(String paramString);
    GlobalEntity fastexcel_uploadExcel  (MultipartFile paramString);


    GlobalEntity showProgress  ();


}
