package org.xiaoxingbomei.service.implement;

import cn.hutool.core.collection.CollectionUtil;
import cn.idev.excel.FastExcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.common.entity.ProgressBar;
import org.xiaoxingbomei.config.Thread.DynamicThreadPool_bak;
import org.xiaoxingbomei.config.fastexcel.CommonLogUploadDataListener;
import org.xiaoxingbomei.config.minio.MinioConfig;
import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
import org.xiaoxingbomei.service.TechService;
import org.xiaoxingbomei.utils.Exception_Utils;
import org.xiaoxingbomei.vo.BusinessLogCommon;
import org.xiaoxingbomei.vo.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.utils.Minio_Utils;
import org.xiaoxingbomei.utils.Redis_Utils;
import org.xiaoxingbomei.common.utils.Request_Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TechServiceImpl implements TechService
{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("mongoTemplateOfLocal")
    private MongoTemplate mongoTemplateOfLocal;

    // @Autowired
    // @Qualifier("mongoTemplateOfDgs")
    // private MongoTemplate mongoTemplateOfDgs;

    @Autowired
    private Redis_Utils redisUtils;

    @Autowired
    private Minio_Utils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaConsumer<String,String> kafkaConsumer;

    @Autowired
    private CommonLogUploadDataListener commonLogUploadDataListener;


//    @Autowired
//    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private DynamicThreadPool_bak threadPool;



    // @Autowired
    // private RestHighLevelClient restHighLevelClient;

    // ==============================================================================================


    @Override
    public GlobalEntity java_multi_createThreadByThread(String paramString)
    {

        // 1、定义一个线程类
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                log.info("thread by thread is running~");
            }
        };
        log.info("主线程 is running~");

        // 2、启动线程
        thread.start();

        // 3、创建返回结果
        HashMap<String, Object> resultMap = new HashMap<>();
        return GlobalEntity.success("使用thread创建线程成功，简洁但不灵活，不支持结果返回，受单继承限制，适用于简单场景下快速实现线程" +
                "thread是java中对线程的封装，底层调用本地方法（JNI调用）" +
                "thread负责启动和管理线程");
    }

    @Override
    public GlobalEntity java_multi_createThreadByRunnable(String paramString)
    {
        // 1、创建runnable对象
        Runnable runnable = () ->
        {
            log.info("thread by Runnable is running~");
        };

        // 2、用thread包装runnable
        Thread thread = new Thread(runnable);
        log.info("主线程 is running~");

        // 3、启动线程
        thread.start();

        // 4、创建返回结果
        HashMap<String, Object> resultMap = new HashMap<>();
        return GlobalEntity.success(
                "通过Runnable创建线程成功，简洁灵活，解耦线程逻辑和运行机制，且不受单继承限制，适合不需要返回结果的线程任务" +
                "Runnable本质是一个任务逻辑，thread接收一个runnable对象，并执行它的run方法" +
                        "runnable只是逻辑的载体，本身不负责线程的生命周期");
    }

    @Override
    public GlobalEntity java_multi_createThreadByCallable(String paramString)
    {
        // 1、创建callable
        Callable<String> task = () ->
        {
            log.info("thread by Callable is running~");
            return "Callable create thread result";
        };

        // 2、用futuretask包装callable
        FutureTask<String> futureTask = new FutureTask<>(task);
        Thread thread = new Thread(futureTask);
        thread.start();

        // 3、执行线程并获取callable的返回结果
        HashMap<String, Object> resultMap = new HashMap<>();
        try
        {
            String futureTaskResult = futureTask.get();
            resultMap.put("futureTaskResult", futureTaskResult);
        } catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("通过callable创建线程失败：{}",e.getMessage());
        }

        // 4、创建返回结果
        return GlobalEntity.success(resultMap,
                "通过callable创建线程成功，复杂但灵活，支持返回结果和异常处理，适用于任务需要返回值或抛出异常的场景" +
                "Callable是一个支持返回结果的任务，本质通过futuretask封装，并调用futuretask.run()" +
                        "Callable只是逻辑的载体，本身不负责线程的生命周期，并且是runnable的扩展（携带返回值和异常支持）");
    }

    @Override
    public GlobalEntity java_multi_createThreadByThreadPool(String paramString)
    {
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 提交多个任务到线程池
        for (int i = 0; i < 5; i++)
        {
            int tastId = i;
            executorService.submit
                    (
                            () -> log.info("Task-"+tastId+"executed by threadName"+Thread.currentThread().getName()+"threadId"+Thread.currentThread().getId())
                    );
        }

        // 关闭线程池
        executorService.shutdown();

        // 创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();

        return GlobalEntity.success(resultMap,
                "通过线程池创建线程成功，高效且灵活，资源复用、减少开销且管理方便，需要额外维护线程池，适用于高并发场景频繁执行短时间任务" +
                "线程池是基于thread和runnable封装，底层通过blockingqueue管理任务队列，线程池内容维护多个线程，反复取出任务执行" +
                "线程池本质是多个thread组合，通过任务队列管理任务分发，避免频繁创建和销毁线程" +
                        "所有创建现成的方式最终都会调用Thread类的底层方法来启动线程，底层使用操作系统原生线程API");
    }

    @Override
    public GlobalEntity java_multi_threadStateNew(String paramString)
    {
        //
        Thread thread = new Thread
                (
                        ()->
                        {
                            log.info("thread is in NEW state");
                        }
                );
        Thread.State threadState = thread.getState();
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("threadState", threadState);
        return GlobalEntity.success(resultMap,"java线程状态-NEW");
    }

    @Override
    public GlobalEntity java_multi_threadStateRunnable(String paramString)
    {
        HashMap<String, Object> resultMap = new HashMap<>();

        // 创建线程
        Thread thread = new Thread
                (
                        ()->
                        {
                            while (true)
                            {
                                log.info("thread is in RUNNABLE state");
                            }
                        }
                );
        try
        {
            thread.start();
            thread.sleep(100); // 确保线程启动
            Thread.State threadState = thread.getState();
            resultMap.put("threadState", threadState);
        } catch (InterruptedException e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("");
        }

        return GlobalEntity.success(resultMap,"java线程状态-RUNNABLE");
    }

    @Override
    public GlobalEntity java_multi_threadStateBlocked(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity java_multi_threadStateWaiting(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity java_multi_threadStateTimedWaiting(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity java_multi_threadStateTerminated(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity dynamicThreadPool_create(String paramString)
    {
        // 1、接收前端参数
        Integer corePoolSize     = Integer.parseInt(Request_Utils.getParam(paramString, "corePoolSize"));
        Integer maximumPoolSize  = Integer.parseInt(Request_Utils.getParam(paramString, "maximumPoolSize"));
        Integer queueCapacity    = Integer.parseInt(Request_Utils.getParam(paramString, "queueCapacity"));

        // 2、创建线程池、
        HashMap<String, Object> resultMap = new HashMap<>();
        if(threadPool==null)
        {
            resultMap.put("createResult","fail");
            return GlobalEntity.error(resultMap,"手动创建动态线程池成功,线程池已存在");
        }

        threadPool = new DynamicThreadPool_bak(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS, new DynamicLinkedBlockingQueue<>(queueCapacity));

        threadPool.printThreadPoolStatus();
        String threadPoolStatus = threadPool.getThreadPoolStatus();

        // 3、手动销毁线程池
        // threadPool.shutdown();

        // 4、创建结果反参

        resultMap.put("threadPoolStatus", threadPoolStatus);
        resultMap.put("createResult","success");
        return GlobalEntity.success(resultMap,"手动创建动态线程池成功");
    }

    @Override
    public GlobalEntity dynamicThreadPool_update(String paramString)
    {
        // 1、获取前端参数
        Integer corePoolSize     = Integer.parseInt(Request_Utils.getParam(paramString, "corePoolSize"));
        Integer maximumPoolSize  = Integer.parseInt(Request_Utils.getParam(paramString, "maximumPoolSize"));
        Integer queueCapacity    = Integer.parseInt(Request_Utils.getParam(paramString, "queueCapacity"));

        // 2、尝试更新线程池
        HashMap<String, Object> resultMap = new HashMap<>();
        if(threadPool==null || threadPool.isShutdown() || threadPool.isTerminated())
        {
            resultMap.put("updateResult","fail");
            return GlobalEntity.error(resultMap,"线程池不存在");
        }
        String threadPoolStatusBefore = threadPool.getThreadPoolStatus();
        threadPool.printThreadPoolStatus();
        resultMap.put("threadPoolStatusBefore", threadPoolStatusBefore);

        threadPool.updateCorePoolSize(corePoolSize);
        threadPool.updateMaximumPoolSize(maximumPoolSize);

        String threadPoolStatusAfter = threadPool.getThreadPoolStatus();
        threadPool.printThreadPoolStatus();
        resultMap.put("threadPoolStatusAfter", threadPoolStatusAfter);
        resultMap.put("updateResult","success");
        return GlobalEntity.success(resultMap,"更新动态线程池参数成功");
    }

    @Override
    public GlobalEntity dynamicThreadPool_get(String paramString)
    {
        HashMap<String, Object> resultMap = new HashMap<>();
        if(threadPool==null)
        {
            resultMap.put("getResult","fail");
            return GlobalEntity.error(resultMap,"线程池不存在");
        }
        String threadPoolStatus = threadPool.getThreadPoolStatus();

        resultMap.put("threadPoolStatus", threadPoolStatus);
        return GlobalEntity.success(resultMap,"获取动态线程池参数成功");
    }

    @Override
    public GlobalEntity dynamicThreadPool_delete(String paramString)
    {
        HashMap<String, Object> resultMap = new HashMap<>();
        if(threadPool==null || threadPool.isShutdown() || threadPool.isTerminated())
        {
            resultMap.put("deleteResult","fail");
            return GlobalEntity.error(resultMap,"线程池不存在或已注销,无需销毁");
        }
        String threadPoolStatus = threadPool.getThreadPoolStatus();
        resultMap.put("threadPoolStatus", threadPoolStatus);
        threadPool.shutdown();
        resultMap.put("deleteResult","success");
        return GlobalEntity.success(resultMap,"销毁动态线程池成功");
    }

    /**
     * 状态               描述
     * NEW              新建状态，线程被创建但尚未启动，调用 Thread t = new Thread() 时进入此状态。
     * RUNNABLE         可运行状态，线程已经启动并准备被 CPU 调度执行，调用 start() 后进入该状态。
     * BLOCKED          阻塞状态，线程尝试进入一个被其他线程锁定的同步块或方法时会进入该状态（如等待锁释放）。
     * WAITING          无限期等待状态，线程在没有超时的情况下等待其他线程的通知，调用 wait()、join() 或 park() 时进入此状态。
     * TIMED_WAITING    有限期等待状态，线程在指定时间内等待其他线程的通知，调用 sleep()、join(timeout) 或 wait(timeout) 等方法时进入此状态。
     * TERMINATED       终止状态，线程执行完成或因异常结束后进入此状态。
     */



    @Override
    public GlobalEntity mongodb_Insert(String paramString)
    {
        User user = JSONObject.parseObject(paramString, User.class);

    //  String name = mongoTemplateOfDgs.getDb().getName();
    //  log.info("dgs database name:{}",name);

        mongoTemplateOfLocal.insert(user);

        return GlobalEntity.success("测试 mongodbTemplate insert 成功");
    }

    @Override
    public GlobalEntity mongodb_MultiInsert(String paramString)
    {

        String      userList    = Request_Utils.getParam(paramString, "userList");
        List<User>  users       = JSONObject.parseArray(userList, User.class);

        if(!CollectionUtil.isEmpty(users))
        {
            mongoTemplateOfLocal.insert(users,User.class);
        }
        else
        {
            return GlobalEntity.error("测试 mongodbTemplate multi insert 失败，数据为空");
        }

        return GlobalEntity.success("测试 mongodbTemplate multi insert 成功");
    }

    @Override
    public GlobalEntity mongodb_Save(String paramString)
    {
        return GlobalEntity.success("测试 mongodbTemplate multi insert 成功");
    }

    @Override
    public GlobalEntity mongodb_Search(String paramString)
    {
        return GlobalEntity.success("测试 mongodb search 成功");
    }

    @Override
    public GlobalEntity mongodb_Update(String paramString)
    {
        return GlobalEntity.success("测试 mongodb update 成功");
    }

    @Override
    public GlobalEntity mongodb_Delete(String paramString)
    {
        return GlobalEntity.success("测试 mongodbTemplate delete 成功");
    }


    @Override
    public GlobalEntity fastJson_ObjectToJsonString(String paramString)
    {
        // 1、构建对象
//        User user = new User("吕相赫", "1","139xxxx", "777", "999", "浙江杭州");
        User user = new User();

        // 2、对象转JSONString
        String jsonString           = JSON.toJSONString(user);
        String jsonStringPrettified = JSON.toJSONString(user, SerializerFeature.PrettyFormat);

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultJSONStirng",jsonString);
        resultMap.put("resultJSONStirngPrettified",jsonStringPrettified);

        return GlobalEntity.success(resultMap,"fastjson-序列化,将一个对象转为jsonString");
    }

    @Override
    public GlobalEntity fastJson_JsonStringToObject(String paramString)
    {
        // 1、接收前端jsonString
        String jsonString1 = Request_Utils.getParam(paramString,"jsonString1");
        String jsonString2 = Request_Utils.getParam(paramString,"jsonString2");

        // 2、JSONString转对象
        User user1 = JSON.parseObject(jsonString1, User.class);
        User user2 = JSON.parseObject(jsonString2, User.class);

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultObject1",user1.toString());
        resultMap.put("resultObject2",user2.toString());

        return GlobalEntity.success(resultMap,"fastjson-反序列化，将jsonString转成对象");
    }

    @Override
    public GlobalEntity fastJson_PrettyPrint(String paramString)
    {
        // 1、构建对象
//        User user = new User("吕相赫","1", "139xxxx", "777", "999", "浙江杭州");
        User user = new User();

        // 2、对象转JSONString
        String jsonStringPrettified = JSONObject.toJSONString(user, SerializerFeature.PrettyFormat);

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultJSONStirngPrettified",jsonStringPrettified);
        return GlobalEntity.success(resultMap,"fastjson-格式化,美化打印jsonString");
    }

    @Override
    public GlobalEntity fastJson_JSONStringToMap(String paramString)
    {
        // 1、接收前端参数
        String jsonString = Request_Utils.getParam(paramString, "jsonString");

        // 2、jsonString转map
        HashMap<String, Object> map = JSONObject.parseObject(jsonString, HashMap.class);

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultMap",map.toString());
        return GlobalEntity.success(resultMap,"fastjson-JSON转为Map");

    }

    @Override
    public GlobalEntity fastJson_JSONStringToList(String paramString)
    {
        // 1、接收前端参数
        String jsonString = Request_Utils.getParam(paramString, "jsonString");

        // 2、JSON转为List
        List<User> userList = JSON.parseArray(jsonString, User.class);

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultMap",userList.toString());
        return GlobalEntity.success(resultMap,"fastjson-JSON转为List");
    }

    @Override
    public GlobalEntity redis_expire(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String timeStr = Request_Utils.getParam(paramString, "time"); // 改用getParam接收字符串
        
        // 2、执行业务逻辑
        try {
            long time = Long.parseLong(timeStr); // 手动转换为long
            Boolean result = redisTemplate.expire(key, time, TimeUnit.SECONDS);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("time", time);
            resultMap.put("result", result);
            return GlobalEntity.success(resultMap, "设置过期时间成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("设置过期时间失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_StringSet(String paramString)
    {
        // 1、判断redis链接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringValue = Request_Utils.getParam(paramString, "redisStringValue");
        boolean reditStringSetFlag = false;
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("successFlag",reditStringSetFlag);

        // 3、redis string set
        try
        {
            redisTemplate.opsForValue().set("keyByRedisTemplate",redisStringValue); //
            stringRedisTemplate.opsForValue().set("keyByStringRedisTemplate",redisStringValue);
            reditStringSetFlag = true;
        } catch (Exception e)
        {
            return GlobalEntity.error(resultMap,"redis-String存储,失败"+e.getMessage());
        }

        // 4、封装结果参数
        resultMap.put("successFlag",reditStringSetFlag);
        return GlobalEntity.success(resultMap,"redis-String存储");
    }

    @Override
    public GlobalEntity redis_StringSetWithExpire(String paramString)
    {
        // 1、判断redis链接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringValue = Request_Utils.getParam(paramString, "redisStringValue");
        boolean reditStringSetFlag = false;
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("successFlag",reditStringSetFlag);

        // 3、redis string set with expire
        try
        {
            redisTemplate.opsForValue().set("StringWithExpire",redisStringValue,120, TimeUnit.SECONDS);
            reditStringSetFlag = true;
        } catch (Exception e)
        {
            return GlobalEntity.error(resultMap,"redis-String存储,失败"+e.getMessage());
        }

        // 4、封装结果参数
        resultMap.put("successFlag",reditStringSetFlag);
        return GlobalEntity.success(resultMap,"redis-String存储");
    }

    @Override
    public GlobalEntity redis_StringGet(String paramString)
    {
        // 1、判断redis链接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringKey = Request_Utils.getParam(paramString, "redisStringKey");
        List<String> redisStringKeyList = Arrays.asList(redisStringKey.split(",", -1));

        // 3、查询对应的rediskey，循环封装进hashmap中
        Map<String, Object> resultMap = redisStringKeyList.stream()
                .collect(Collectors.toMap
                        (
                        key -> key,
                        key -> redisTemplate.opsForValue().get(key)
                        ));

        // 4、封装结果对象
        return GlobalEntity.success(resultMap,"redis-String获取");
    }

    @Override
    public GlobalEntity redis_StringDelete(String paramString)
    {
        // 1、判断redis链接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringKey = Request_Utils.getParam(paramString, "redisStringKey");
        List<String> redisStringKeyList = Arrays.asList(redisStringKey.split(",", -1));

        // 3、删除对应的redis key
        Long deleteResult = redisTemplate.delete(redisStringKeyList);

        // 封装
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("deleteResult",deleteResult);
        return GlobalEntity.success(resultMap,"redis-String刪除");
    }

    @Override
    public GlobalEntity redis_StringIncr(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringKey = Request_Utils.getParam(paramString, "redisStringKey"); // redis key
        String delta          = Request_Utils.getParam(paramString, "delta");          // 步长


        Long increment = 0L;
        // 2、获取前端参数
        try
        {
            // 将指定的键给定的增量，如果键不存在，则会先将该键设置为0，然后再进行自增操作
            increment = redisTemplate.opsForValue().increment(redisStringKey, Long.parseLong(delta));
        }catch (Exception e)
        {
            return GlobalEntity.error("redis自增失败"+e.getMessage());
        }

        // 3、封装结果参数
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("increment",increment);
        return GlobalEntity.success(resultMap,"redis-String自增");
    }

    @Override
    public GlobalEntity redis_StringDecr(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、获取前端参数
        String redisStringKey = Request_Utils.getParam(paramString, "redisStringKey"); // redis key
        String delta          = Request_Utils.getParam(paramString, "delta");          // 步长

        Long decrement = 0L;
        // 2、获取前端参数
        try
        {
            // 将指定的键给定的增量，如果键不存在，则会先将该键设置为0，然后再进行自增操作
            decrement = redisTemplate.opsForValue().decrement(redisStringKey, Long.parseLong(delta));
        }catch (Exception e)
        {
            return GlobalEntity.error("redis自减失败"+e.getMessage());
        }

        // 3、封装结果参数
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("decrement",decrement);
        return GlobalEntity.success(resultMap,"redis-String自减");
    }

    @Override
    public GlobalEntity redis_hashSave(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、接收前端参数
        String key      = Request_Utils.getParam(paramString, "key");
        String hashKey  = Request_Utils.getParam(paramString, "hashKey");
        String value    = Request_Utils.getParam(paramString, "value");

        // 3、执行 redis hash 操作存储数据
        redisTemplate.opsForHash().put(key,hashKey,value);

        // 4、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("saveResult","success");
        resultMap.put("key",key);
        resultMap.put("hashKey",hashKey);
        resultMap.put("value",value);

        return GlobalEntity.success(resultMap,"redis-hash存储成功");
    }

    @Override
    public GlobalEntity redis_hashFind(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、接收前端参数
        String key      = Request_Utils.getParam(paramString, "key");
        String hashKey  = Request_Utils.getParam(paramString, "hashKey");

        // 3、执行 redis hash 操作查询数据
        Object value = redisTemplate.opsForHash().get(key, hashKey);

        // 4、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("saveResult","success");
        resultMap.put("key",key);
        resultMap.put("hashKey",hashKey);
        resultMap.put("value",value);

        return GlobalEntity.success(resultMap,"redis-hash查询成功");
    }

    @Override
    public GlobalEntity redis_hashFindAll(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、接收前端参数
        String key      = Request_Utils.getParam(paramString, "key");

        // 3、执行 redis hash 操作 查询全部数据
        Map<Object,Object> entries = redisTemplate.opsForHash().entries(key);

        // 4、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("saveResult","success");
        resultMap.put("key",key);
        resultMap.put("value",entries.toString());

        return GlobalEntity.success(resultMap,"redis-hash查询全部成功");
    }

    @Override
    public GlobalEntity redis_hashDelete(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、接收前端参数
        String key      = Request_Utils.getParam(paramString, "key");
        String hashKey  = Request_Utils.getParam(paramString, "hashKey");

        // 3、执行 redis hash 操作 删除指定数据
        redisTemplate.opsForHash().delete(key,hashKey);

        // 4、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("deleteResult","success");
        resultMap.put("key",key);
        resultMap.put("hashKey",hashKey);

        return GlobalEntity.success(resultMap,"redis-hash删除指定数据成功");
    }

    @Override
    public GlobalEntity redis_hashDeleteAll(String paramString)
    {
        // 1、判断redis连接状态，如果redis挂了就没必要进行了
        if(!redisUtils.isRedisConnected())
        {
            return GlobalEntity.error("redis暂不可用");
        }

        // 2、接收前端参数
        String key      = Request_Utils.getParam(paramString, "key");

        // 3、执行 redis hash 操作 删除指定数据
        redisTemplate.delete(key);

        // 4、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("deleteAllResult","success");
        resultMap.put("key",key);

        return GlobalEntity.success(resultMap,"redis-hash删除全部数据成功");
    }

    @Override
    public GlobalEntity redis_listLeftPush(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、核心处理：
        redisTemplate.opsForList().leftPush(key,value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("listLeftPushResult","success");
        resultMap.put("key",key);
        resultMap.put("value",value);
        return GlobalEntity.success(resultMap,"向列表左侧推入元素（左压栈）");
    }

    @Override
    public GlobalEntity redis_listRightPush(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、核心处理：
        redisTemplate.opsForList().rightPush(key,value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("listRightPushResult","success");
        resultMap.put("key",key);
        resultMap.put("value",value);
        return GlobalEntity.success(resultMap,"向列表右侧推入元素（右压栈）");
    }

    @Override
    public GlobalEntity redis_listLeftPop(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、核心处理：
        Object popResult = redisTemplate.opsForList().leftPop(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("listLeftPopResultFlag","success");
        resultMap.put("key",key);
        resultMap.put("value",value);
        resultMap.put("listLeftPopResult",popResult);
        return GlobalEntity.success(resultMap,"从列表左侧弹出元素（左出栈）");
    }

    @Override
    public GlobalEntity redis_listRightPop(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、核心处理：
        Object popResult = redisTemplate.opsForList().rightPop(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("listRightPopResultFlag","success");
        resultMap.put("key",key);
        resultMap.put("value",value);
        resultMap.put("listRightPopResult",popResult);
        return GlobalEntity.success(resultMap,"从列表左侧弹出元素（左出栈）");
    }

    @Override
    public GlobalEntity redis_listRange(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String start = Request_Utils.getParam(paramString, "start");
        String end   = Request_Utils.getParam(paramString, "end");

        // 2、核心处理：
        List<Object> rangeResult = redisTemplate.opsForList().range(key, Long.parseLong(start), Long.parseLong(end));

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("rangeResultFlag","success");
        resultMap.put("key",key);
        resultMap.put("rangeResult",rangeResult);
        return GlobalEntity.success(resultMap,"获取列表中的指定范围的元素");
    }

    @Override
    public GlobalEntity redis_listRemove(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");   // 列表的键
        String count = Request_Utils.getParam(paramString, "count"); // 删除的数量（正数从左到右，负数从右到左，0 删除所有）
        String value = Request_Utils.getParam(paramString, "value"); // 要删除的值

        // 2、核心处理：
        redisTemplate.opsForList().remove(key,Long.parseLong(count),value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("removeResultFlag","success");
        resultMap.put("key",key);
        resultMap.put("count",count);
        resultMap.put("value",value);
        return GlobalEntity.success(resultMap,"删除列表中指定数量的某个值");
    }

    @Override
    public GlobalEntity redis_listRemoveByIndex(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");   // redis key
        String index = Request_Utils.getParam(paramString, "index"); // redis index

        // 2、获取整个list数据
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        if(list==null || Integer.parseInt(index)<0 || Integer.parseInt(index)>=list.size())
        {
            throw new IllegalArgumentException("索引无效");
        }

        // 3、删除指定索引的数据
        list.remove(Integer.parseInt(index));

        // 4、删除原始list
        redisTemplate.delete(key);

        // 5、重新保存列表
        redisTemplate.opsForList().rightPushAll(key,list);

        // 6、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("removeResultFlag","success");
        resultMap.put("key",key);
        resultMap.put("index",index);
        return GlobalEntity.success(resultMap,"通过索引删除范围内元素");
    }

    @Override
    public GlobalEntity redis_listRemoveByRange(String paramString)
    {
        // 1、接收前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String start = Request_Utils.getParam(paramString, "start");
        String end   = Request_Utils.getParam(paramString, "end");

        // 2、保留索引范围内元素（删除范围外数据）
        redisTemplate.opsForList().trim(key, Long.parseLong(start), Long.parseLong(end));

        // 3、创建返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        return GlobalEntity.success(resultMap,"通过范围反向删除元素");
    }

    @Override
    public GlobalEntity redis_listSize(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");

        // 2、核心处理：
        Long size = redisTemplate.opsForList().size(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("sizeResult","success");
        resultMap.put("key",key);
        resultMap.put("size",size);
        return GlobalEntity.success(resultMap,"获取列表的长度");
    }

    @Override
    public GlobalEntity redis_listSet(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String index = Request_Utils.getParam(paramString, "index");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、核心处理：
        redisTemplate.opsForList().set(key,Long.parseLong(index),value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("setResult","success");
        resultMap.put("key",key);
        resultMap.put("index",index);
        resultMap.put("value",value);
        return GlobalEntity.success(resultMap,"设置列表中指定索引的值");
    }


    @Override
    public GlobalEntity redis_setAdd(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、操作redis hash add
        Long addResult = redisTemplate.opsForSet().add(key, value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("setResult",addResult);
        resultMap.put("key",key);
        resultMap.put("value",value);

        return GlobalEntity.success(resultMap,"redis-set-add一个元素");
    }

    @Override
    public GlobalEntity redis_setAddAll(String paramString)
    {
        // 1、获取前端参数
        String key      = Request_Utils.getParam(paramString, "key");
        String[] values = Request_Utils.getParam(paramString, "values").split(",");

        // 2、操作 redis set 添加多个元素
        Long result = redisTemplate.opsForSet().add(key, values);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key",    key);
        resultMap.put("values", values);
        resultMap.put("result", result);
        return GlobalEntity.success(resultMap, "redis-set-批量添加元素");
    }

    @Override
    public GlobalEntity redis_setGetAll(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");

        // 2、操作 redis set 获取全部成员
        Set<Object> members = redisTemplate.opsForSet().members(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("members", members);
        resultMap.put("key", key);
        return GlobalEntity.success(resultMap, "redis-set-获取全部成员");
    }

    @Override
    public GlobalEntity redis_setGetRandom(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");

        // 2、操作 redis set 获取随机成员
        Object randomMember = redisTemplate.opsForSet().randomMember(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("randomMember", randomMember);
        resultMap.put("key", key);
        return GlobalEntity.success(resultMap, "redis-set-获取随机成员");
    }

    @Override
    public GlobalEntity redis_setIsMember(String paramString)
    {
        // 1、获取前端参数
        String key   = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、操作 redis set 判断元素是否存在
        Boolean isMember = redisTemplate.opsForSet().isMember(key, value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key",      key);
        resultMap.put("value",    value);
        resultMap.put("isMember", isMember);
        return GlobalEntity.success(resultMap, "redis-set-判断元素是否存在");
    }

    @Override
    public GlobalEntity redis_setRemove(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、操作 redis set 移除元素
        Long result = redisTemplate.opsForSet().remove(key, value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key",    key);
        resultMap.put("value",  value);
        resultMap.put("result", result);
        return GlobalEntity.success(resultMap, "redis-set-移除元素");
    }

    @Override
    public GlobalEntity redis_setSize(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");

        // 2、操作 redis set 获取集合大小
        Long size = redisTemplate.opsForSet().size(key);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key",  key);
        resultMap.put("size", size);
        return GlobalEntity.success(resultMap, "redis-set-获取集合大小");
    }

    @Override
    public GlobalEntity redis_zsetAdd(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String member = Request_Utils.getParam(paramString, "member");
        String scoreStr = Request_Utils.getParam(paramString, "score");  // 改用getParam
        
        // 2、执行业务逻辑
        try {
            double score = Double.parseDouble(scoreStr);  // 手动转换类型
            Boolean result = redisTemplate.opsForZSet().add(key, member, score);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("member", member);
            resultMap.put("score", score);
            resultMap.put("result", result);
            return GlobalEntity.success(resultMap, "添加成员到有序集合成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("添加成员到有序集合失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetGetByRange(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String startStr = Request_Utils.getParam(paramString, "start");  // 改用getParam
        String endStr = Request_Utils.getParam(paramString, "end");      // 改用getParam
        
        // 2、执行业务逻辑
        try {
            long start = Long.parseLong(startStr);  // 手动转换类型
            long end = Long.parseLong(endStr);      // 手动转换类型
            Set<Object> result = redisTemplate.opsForZSet().range(key, start, end);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("start", start);
            resultMap.put("end", end);
            resultMap.put("members", result);
            return GlobalEntity.success(resultMap, "获取指定范围的成员成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("获取指定范围的成员失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetGetByRangeReverse(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String startStr = Request_Utils.getParam(paramString, "start");
        String endStr = Request_Utils.getParam(paramString, "end");
        
        // 2、执行业务逻辑
        try {
            long start = Long.parseLong(startStr);
            long end = Long.parseLong(endStr);
            Set<Object> result = redisTemplate.opsForZSet().reverseRange(key, start, end);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("start", start);
            resultMap.put("end", end);
            resultMap.put("members", result);
            return GlobalEntity.success(resultMap, "获取指定范围的成员(逆序)成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("获取指定范围的成员(逆序)失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetGetByScoreRange(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String minStr = Request_Utils.getParam(paramString, "min");
        String maxStr = Request_Utils.getParam(paramString, "max");
        
        // 2、执行业务逻辑
        try {
            double min = Double.parseDouble(minStr);
            double max = Double.parseDouble(maxStr);
            Set<Object> result = redisTemplate.opsForZSet().rangeByScore(key, min, max);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("min", min);
            resultMap.put("max", max);
            resultMap.put("members", result);
            return GlobalEntity.success(resultMap, "获取指定分数范围的成员成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("获取指定分数范围的成员失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetGetByScoreRangeReverse(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String minStr = Request_Utils.getParam(paramString, "min");
        String maxStr = Request_Utils.getParam(paramString, "max");
        
        // 2、执行业务逻辑
        try {
            double min = Double.parseDouble(minStr);
            double max = Double.parseDouble(maxStr);
            Set<Object> result = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("min", min);
            resultMap.put("max", max);
            resultMap.put("members", result);
            return GlobalEntity.success(resultMap, "获取指定分数范围的成员(逆序)成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("获取指定分数范围的成员(逆序)失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetSize(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        
        // 2、执行业务逻辑
        try {
            Long size = redisTemplate.opsForZSet().size(key);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("size", size);
            return GlobalEntity.success(resultMap, "获取有序集合大小成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("获取有序集合大小失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetRemove(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、操作 redis zset 移除元素
        Long result = redisTemplate.opsForZSet().remove(key, value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key", key);
        resultMap.put("value", value);
        resultMap.put("result", result);
        return GlobalEntity.success(resultMap, "redis-zset-移除元素");
    }

    @Override
    public GlobalEntity redis_zsetRemoveByRange(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String startStr = Request_Utils.getParam(paramString, "start");
        String endStr = Request_Utils.getParam(paramString, "end");
        
        // 2、执行业务逻辑
        try {
            long start = Long.parseLong(startStr);
            long end = Long.parseLong(endStr);
            Long count = redisTemplate.opsForZSet().removeRange(key, start, end);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("start", start);
            resultMap.put("end", end);
            resultMap.put("removedCount", count);
            return GlobalEntity.success(resultMap, "移除指定范围的成员成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("移除指定范围的成员失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetRemoveByScoreRange(String paramString) {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String minStr = Request_Utils.getParam(paramString, "min");
        String maxStr = Request_Utils.getParam(paramString, "max");
        
        // 2、执行业务逻辑
        try {
            double min = Double.parseDouble(minStr);
            double max = Double.parseDouble(maxStr);
            Long count = redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
            
            // 3、封装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", key);
            resultMap.put("min", min);
            resultMap.put("max", max);
            resultMap.put("removedCount", count);
            return GlobalEntity.success(resultMap, "移除指定分数范围的成员成功");
        } catch (Exception e) {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("移除指定分数范围的成员失败: {}", e.getMessage());
        }
    }

    @Override
    public GlobalEntity redis_zsetGetScore(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");

        // 2、操作 redis zset 获取元素分数
        Double score = redisTemplate.opsForZSet().score(key, value);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key", key);
        resultMap.put("value", value);
        resultMap.put("score", score);
        return GlobalEntity.success(resultMap, "redis-zset-获取元素分数");
    }

    @Override
    public GlobalEntity redis_zsetIncrementScore(String paramString)
    {
        // 1、获取前端参数
        String key = Request_Utils.getParam(paramString, "key");
        String value = Request_Utils.getParam(paramString, "value");
        double delta = Double.parseDouble(Request_Utils.getParam(paramString, "delta"));

        // 2、操作 redis zset 增加元素分数
        Double newScore = redisTemplate.opsForZSet().incrementScore(key, value, delta);

        // 3、创建结果反参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("key", key);
        resultMap.put("value", value);
        resultMap.put("newScore", newScore);
        return GlobalEntity.success(resultMap, "redis-zset-增加元素分数");
    }

    @Override
    public GlobalEntity redis_keys(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_type(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_rename(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_move(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_randomKey(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_scan(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_ttl(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_bitSet(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_bitGet(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_bitCount(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_bitPos(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_bitOp(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_pfAdd(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_pfCount(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_pfMerge(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_geoAdd(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_geoPos(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_geoDist(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_geoRadius(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_geoHash(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamAdd(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamRead(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamRange(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamLen(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamTrim(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamGroups(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_streamCreateGroup(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_multi(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_exec(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_discard(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_watch(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_unwatch(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_publish(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_subscribe(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_psubscribe(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_pubsubChannels(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity redis_pubsubNumsub(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity cookie_create(String paramString, HttpServletResponse response)
    {
        // 1、接收前端参数
        String userName = Request_Utils.getParam(paramString, "userName");

        // 2、创建一个Cookie对象
        Cookie cookie = new Cookie("userName", userName);
        Cookie cookie2 = new Cookie("userName2", "second"+userName);
        cookie.setMaxAge(60*60);   // 有效期1小时
        cookie.setPath("/");       // 根路径下的所有请求都能获得这个Cookie
        cookie.setHttpOnly(true);  // 设置cookie为httponly，浏览器脚本无法访问
        response.addCookie(cookie);// 将cookie添加到响应头中
        response.addCookie(cookie2);// 将cookie添加到响应头中

        return GlobalEntity.success("创建cookie成功");
    }

    @Override
    public GlobalEntity cookie_update(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity cookie_search(String paramString,HttpServletRequest request)
    {
        // 1、获取前端参数
        String cookieKey = Request_Utils.getParam(paramString, "cookieKey");

        // 获取全部cookie
        HashMap<String, Object> resultMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (StringUtils.equals(cookieKey, cookie.getName()))
                {
                    resultMap.put("successFlag",true);
                    resultMap.put("userName",cookie.getValue());
                }
            }
        }
        return GlobalEntity.success(resultMap,"cookie-获取指定cookie成功");
    }

    @Override
    public GlobalEntity cookie_delete(String paramString,HttpServletRequest request,HttpServletResponse response)
    {
        // 1、获取前端参数
        String cookieKey = Request_Utils.getParam(paramString, "cookieKey");

        // 2、注销
        HashMap<String, Object> resultaMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if(StringUtils.equals(cookieKey, cookie.getName()))
                {
                    // 设置Cookie的过期时间为 0，浏览器会自动删除他
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // 确保删除时路径一致
                    // 将删除的Cookie添加到响应中
                    response.addCookie(cookie);
                    resultaMap.put("successFlag",true);
                    resultaMap.put("cookieKey",cookieKey);
                    resultaMap.put("cookieValue",cookie.getValue());
                }
            }
        }
        return GlobalEntity.success(resultaMap,"cookie-删除成功");
    }

    @Override
    public GlobalEntity session_create(String paramString, HttpServletRequest request)
    {
        // 1、接收前端参数
        String userId   = Request_Utils.getParam(paramString, "userId");
        String userName = Request_Utils.getParam(paramString, "userName");

        // 2、获取/创建session
        HashMap<String, Object> resultMap = new HashMap<>();
        if(true) // 这里模拟登陆成功
        {
            HttpSession session = request.getSession(true); // 如果不存在则创建一个新的session
            session.setAttribute("userId",userId);
            session.setAttribute("userName",userName);

            resultMap.put("successFlag",true);
            resultMap.put("userId",userId);
            resultMap.put("userName",userName);
            resultMap.put("sessionId",session.getId());
            // resultMap.put("servletContext",session.getServletContext()); // 加上这个会出现一个很奇怪的bug  以后再排查 todo
        }
        return GlobalEntity.success(resultMap,"session-创建并设置初始化信息成功");
    }

    @Override
    public GlobalEntity session_update(String paramString, HttpServletRequest request)
    {
        // 1、获取前端参数
        String sessionId = Request_Utils.getParam(paramString, "sessionId");
        String userId = Request_Utils.getParam(paramString, "userId");
        String useName = Request_Utils.getParam(paramString, "useName");

        // 2、修改session信息
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = request.getSession(false); // 获取当前session，如果没有则为null
        if(session!=null)
        {
            session.setAttribute("userId",userId);
            session.setAttribute("userName",useName);
            resultMap.put("successFlag",true);
        }

        // 3、封装返回体
        resultMap.put("sessionId",session.getId());
        resultMap.put("userId",userId);
        resultMap.put("useName",useName);
        return GlobalEntity.success(resultMap,"session-修改session信息成功");
    }

    @Override
    public GlobalEntity session_search(String paramString, HttpServletRequest request)
    {
        // 1、获取前端参数
        String sessionId = Request_Utils.getParam(paramString, "sessionId");

        // 2、
        HashMap<String, Object> resultMap = new HashMap<>();
        HttpSession session = request.getSession(false); // 如果没有session，则返回null
        if(sessionId!=null)
        {
            resultMap.put("message","session is alive");
        }

        // 3、封装返回体
        resultMap.put("sessionId",session.getId());
        resultMap.put("successFlag",true);
        resultMap.put("inputSessionId",sessionId);
        resultMap.put("userName",session.getAttribute("userName"));
        resultMap.put("userId",session.getAttribute("userId"));

        return GlobalEntity.success(resultMap,"session-获取session信息成功");
    }

    @Override
    public GlobalEntity session_delete(String paramString, HttpServletRequest request)
    {
        // 1、获取前端参数
        String sessionId = Request_Utils.getParam(paramString, "sessionId");

        // 2、注销/销毁session
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("successFlag",false);
        HttpSession session = request.getSession(false);
        if(session!=null && StringUtils.equals(session.getId(),sessionId))
        {
            session.invalidate();
            resultMap.put("successFlag",true);
        }

        // 3、封装返回体
        resultMap.put("sessionId",sessionId);
        return GlobalEntity.success(resultMap,"session-删除session信息成功");
    }

    @Override
    public GlobalEntity elasticsearch_createIndex(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_deleteIndex(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_existsIndex(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_getIndex(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_indexDocument(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_getDocument(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_existsDocument(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_updateDocument(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_deleteDocument(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity elasticsearch_bulkInsert(String paramString) {
        return null;
    }

    @Override
    public GlobalEntity kafka_Product(String paramString)
    {
        // 1、接收前端参数
        String topic    = Request_Utils.getParam(paramString, "topic");
        String message  = Request_Utils.getParam(paramString, "message");

        // 2、
        kafkaTemplate.send(topic,message);
        log.info("kafka produced message:{}"+message);

        // 3、
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("successFlag",true);
        resultMap.put("message",message);
        resultMap.put("topic",topic);
        return GlobalEntity.success(resultMap,"kafka-生产消息-成功");
    }

    @Override
    public GlobalEntity kafka_Consume(String paramString)
    {
        // 1、接收前端参数
        String topic = Request_Utils.getParam(paramString, "topic");
        kafkaConsumer.subscribe(Arrays.asList(topic)); // 订阅指定的topic

        // 2、消费kafka消息
        boolean consumeFlag = true;
        while (consumeFlag)
        {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000); // 拉取消息，等待1000毫秒
            for (ConsumerRecord<String, String> record : records)
            {
                log.info
                        ("消费到消息: topic={},partition={},offset={},key={},value={}",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value()
                        );
                consumeFlag = false;
            }
        }

        // 3、构建反参
        HashMap<String, Object> resultMap = new HashMap<>();
        return GlobalEntity.success("kafka-消費消息-成功");
    }

    @Override
    public GlobalEntity minio_createBucket(String paramString)
    {
        // 1、获取前端参数
        String bucketName = Request_Utils.getParam(paramString, "bucketName");

        // 2、尝试创建bucket，如果不存在则创建
        MinioClient minioClient = minioConfig.createMinioClient();

        try
        {
            boolean bucketExists = minioClient.bucketExists(bucketName);
            if(!bucketExists)
            {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("minio bucket created:{}",bucketName);
            }
            else
            {
                log.info("minio bucket already exists:{}",bucketName);
            }
        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("minio-创建bucket失败");
        }

        // 3、构建返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("bucketName",bucketName);
        return GlobalEntity.success(resultMap,"minio-创建bucket成功");
    }

    @Override
    public GlobalEntity minio_searchBucket(String paramString)
    {
        // 1、获取前端参数
        String bucketName = Request_Utils.getParam(paramString, "bucketName");
        HashMap<String, Object> resultMap = new HashMap<>();

        // 2、列出minio的桶及对象信息
        try
        {
            // 创建minio客户端
            MinioClient minioClient = minioConfig.createMinioClient();

            // 如果有指定的bucket，则获取其及对象
            if(StringUtils.isEmpty(bucketName))
            {
                List<Bucket> allBuckets = minioClient.listBuckets();
                for (Bucket bucket : allBuckets)
                {
                    String tempBucketName = bucket.name();
                    List<String> objecNamesInBucket = minioUtils.listObjectsInBucket(minioClient,tempBucketName);
                    resultMap.put(tempBucketName,objecNamesInBucket);
                }
            }
            // 如果没有指定的桶，则列出全部的桶及其对象
            else
            {
                List<String> objecNamesInBucket = minioUtils.listObjectsInBucket(minioClient,bucketName);
                resultMap.put(bucketName,objecNamesInBucket);
            }
        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("minio-获取所有桶及对象-失败"+e.getMessage());
        }

        // 3、构建返回体
        return GlobalEntity.success(resultMap,"minio-获取所有桶及对象");
    }


    @Override
    public GlobalEntity minio_deleteBucket(String paramString)
    {
        // 1、获取前端参数
        String bucketName = Request_Utils.getParam(paramString, "bucketName");
        List<String> bucketNameList = Arrays.asList(bucketName.split(",", -1));

        // 2、删除bucket
        MinioClient minioClient = minioConfig.createMinioClient();
        try
        {
            for (String bucketToDelete : bucketNameList)
            {
                // 判断桶是否存在
                boolean bucketExist = minioUtils.isBucketExist(minioClient,bucketToDelete);
                if(!bucketExist)
                {
                    continue;
                }
                // 如果桶存在则删除桶及其下面全部对象
                minioUtils.deleteBucket(minioClient,bucketToDelete);
            }
        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("minio-删除bucket-失败:{}"+e.getMessage());
        }

        // 3、封装结果
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("bucketName",bucketName);
        return GlobalEntity.success("minio-删除bucket-成功");
    }

    @Override
    public GlobalEntity minio_uploadFile(String paramString)
    {
        // 1、获取前端参数
        String bucketName = Request_Utils.getParam(paramString, "bucketName");
        String filePath   = Request_Utils.getParam(paramString, "filePath");
        String fileName   = Request_Utils.getParam(paramString, "fileName");

        // 2、确保文件编码 minio不需要这个 自动会编码中文
        // try
        // {
        //     fileName = URLEncoder.encode(fileName, "UTF-8");
        // }catch (UnsupportedEncodingException e)
        // {
        //     Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        //     return GlobalEntity.error("minio-上传文件-失败:"+e.getMessage());
        // }

        // 3、上传文件
        try
        {
            MinioClient minioClient = minioConfig.createMinioClient();
            minioClient.putObject
                    (
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .stream(
                                            Files.newInputStream(Paths.get(filePath)),
                                            Files.size(Paths.get(filePath)),
                                            -1
                                    )
                                    .contentType("application/octet-stream")
                                    .build()
                    );
            log.info("minio bucket uploaded:{}",bucketName);
        }
        catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("minio bucket uploaded falure:{}"+e.getMessage());
        }

        // 4、返回
        return GlobalEntity.success("minio bucket uploaded success");
    }

    @Override
    public GlobalEntity minio_downloadFile(String paramString)
    {
        // 1、获取前端参数
        String bucketName     = Request_Utils.getParam(paramString, "bucketName");  // 目标桶
        String objectName     = Request_Utils.getParam(paramString, "objectName");  // 目标下载文件
        String downloadPath   = Request_Utils.getParam(paramString, "downloadPath");// 下载到哪里

        // 2、下载文件 try-with-resource 自动释放资源
        // 获取minio客户端
        MinioClient minioClient = minioConfig.createMinioClient();
        try(
            // 创建对象输入流
            InputStream minioInputStream = minioClient.getObject
                    (
                            GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .build()
                    );
            // 打开输出流并写入数据
            FileOutputStream fileOutputStream = new FileOutputStream(downloadPath))
            {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ( (bytesRead =minioInputStream.read(buffer)) != -1)
                {
                    fileOutputStream.write(buffer,0,bytesRead);
                    log.info("minio文件已经成功下载:" + objectName + " 到 " + downloadPath);
                }
            }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("minio-下载文件-失败:{}"+e.getMessage());
        }

        // 3、封装返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("bucketName",bucketName);
        resultMap.put("objectName",objectName);
        resultMap.put("downloadPath",downloadPath);
        return GlobalEntity.success(resultMap,"minio-下载文件");
    }

    @Override
    public GlobalEntity minio_deleteFile(String paramString)
    {
        return null;
    }

    @Override
    public GlobalEntity fastexcel_downloadExcel(String paramString)
    {
        try
        {
            // 1、接收前端参数
            String fileName     = Request_Utils.getParam(paramString, "fileName");
            String bucketName   = Request_Utils.getParam(paramString, "bucketName");

            // 2、创建minio客户端
            MinioClient minioClient = minioConfig.createMinioClient();

            // 3、创建输出流，根据类来读取excel模板，用于写入minio
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            FastExcel.write(outputStream, BusinessLogCommon.class)
                     .sheet("模板")
                    .head(BusinessLogCommon.class)
                    .doWrite(Arrays.asList());

            // 4、创建输入流，读取excel模板，上传minio
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            minioClient.putObject
                    (
                            PutObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .stream(
                                            inputStream,
                                            inputStream.available(),
                                            -1
                                    )
                                    .contentType("application/octet-stream")
                                    .build()
                    );
            // 5、设置并获取文件下载链接，附带过期时间
            String fileUrl = minioClient.getObjectUrl(bucketName, fileName).toString();
            HashMap<String, String> options = new HashMap<>();
            options.put("expiry", String.valueOf(TimeUnit.DAYS.toSeconds(1)));
            String presignedUrl = minioClient.getPresignedObjectUrl(Method.GET,bucketName,fileName,Integer.valueOf((int) TimeUnit.DAYS.toSeconds(1)),options);

            // 6、封装结果参数
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("bucketName",bucketName);
            resultMap.put("fileName",fileName);
            resultMap.put("url",fileUrl);
            resultMap.put("presignedUrl",presignedUrl);
            return GlobalEntity.success(resultMap,"下载模板文件成功："+fileUrl);

        } catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("{}",e.getMessage());
        }
    }

    @Override
    public GlobalEntity fastexcel_uploadExcel(MultipartFile file)
    {
        if(file.isEmpty())
        {
            return GlobalEntity.error("请选择日志文件上传");
        }

        try
        {
            FastExcel.read(file.getInputStream(), BusinessLogCommon.class,commonLogUploadDataListener).sheet().doRead();
            return GlobalEntity.success("文件上传并处理成功");
        }catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error("文件上传失败:{}",e.getMessage());
        }

    }

    @Override
    public GlobalEntity showProgress()
    {
        ProgressBar progressBar = new ProgressBar(500, 60, 2);
        progressBar.start();

        for (int i = 0; i < 500; i++) {
            doSth();
            progressBar.update(i + 1);
        }
        return GlobalEntity.success("测试进度条，任务执行完毕");
    }
    private void doSth() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
