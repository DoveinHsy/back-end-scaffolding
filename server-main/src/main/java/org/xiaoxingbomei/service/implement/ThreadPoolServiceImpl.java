//package org.xiaoxingbomei.service.implement;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.xiaoxingbomei.config.Thread.DynamicThreadPool;
//import org.xiaoxingbomei.entity.DynamicLinkedBlockingQueue;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.concurrent.TimeUnit;
//
///**
// *
// */
//@Service
//@Slf4j
//public class ThreadPoolServiceImpl {
//
//    private DynamicThreadPool dynamicThreadPool; // 动态线程池
//
//    // ===================================================
//
//
//    // 在服务启动时初始化线程池
//    @PostConstruct
//    public void dynamicThreadPoolInit()
//    {
//        dynamicThreadPool = new DynamicThreadPool(10, 20, 60L, TimeUnit.SECONDS, new DynamicLinkedBlockingQueue<>(100));
//
//        // 打印线程池初始化信息
//        log.info("\n----------------------<<在服务启动时初始化线程池>>---------------------\n\t{}{}{}{}{}",
//                "Thread pool initialized:",
//                "\n\t Core Pool Size: \t" + 10,
//                "\n\t Max Pool Size: \t" + 20,
//                "\n\t Keep Alive Time: \t" + 60L + " seconds",
//                "\n\t Queue Capacity: \t" + 100,
//                "\n----------------------------------------------------------\n");
//    }
//
//    // 在服务停止时销毁线程池
//    @PreDestroy
//    public void dynamicThreadPoolInitCleanup()
//    {
//        if (dynamicThreadPool != null) {
//            dynamicThreadPool.shutdown();
//
//            // 打印线程池销毁信息
//            log.info("\n----------------------<<在服务停止时销毁线程池>>----------------------\n\t{}{}{}{}{}",
//                    "Thread pool has been shut down:",
//                    "\n\t Core Pool Size: \t" + 10,
//                    "\n\t Max Pool Size: \t" + 20,
//                    "\n\t Queue Capacity: \t" + 100,
//                    "\n----------------------------------------------------------\n");
//        }
//    }
//
//}
