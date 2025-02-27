package test.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Slf4j
public class CreateThread
{

    @Test
    public void testCreateThread()
    {
        // 1、Thread
        new CreateThreadByThread().start();

        // 2、Runnable
        CraeteThreadByRunnable craeteThreadByRunnable = new CraeteThreadByRunnable();
        new Thread(craeteThreadByRunnable).start();

        // 3、Callable
        FutureTask<String> futureTask = new FutureTask<>(new CraeteThreadByCallable());
        new Thread(futureTask).start();
        try
        {
            String futureResult = futureTask.get();// 阻塞获取结果
        } catch (Exception e)
        {
            log.error(e.getMessage());
        }

        // 4、Executor

    }


    /**
     * 创建线程-继承Thread
     * 优点：简单直接，适合快速实现。
     * 缺点：Java单继承的局限性，无法继承其他类；线程与任务耦合，资源共享困难。
     * 底层：通过JVM调用本地方法（如start0()）创建操作系统线程。
     */
    class CreateThreadByThread extends Thread
    {
        @Override
        public void run()
        {
            log.info("CreateThreadByThread");
        }
    }

    /**
     * 创建线程-实现Runnable
     * 优点：解耦任务与线程，支持多实现；适合资源共享（如多个线程操作同一个Runnable实例）。
     * 底层：Thread类内部调用Runnable的run()方法，本质仍是操作系统线程。
     */
    class CraeteThreadByRunnable implements Runnable
    {
        @Override
        public void run()
        {
            log.info("CreateThreadByRunnable");
        }
    }

    /**
     * 创建线程-实现Callable
     * 优点：支持返回值、异常处理，适合需要结果的任务。
     * 底层：通过FutureTask包装Callable，由线程池或Thread执行。
     */
    class CraeteThreadByCallable implements Callable<String>
    {
        @Override
        public String call() throws Exception
        {
            log.info("CraeteThreadByCallable");
            return "Callable result";
        }
    }

    /**
     * 创建线程-线程池
     * 优点：线程复用、资源可控（避免频繁创建/销毁开销）；支持异步任务调度。
     * 底层：基于线程池核心参数（核心线程数、任务队列、最大线程数等），通过ThreadFactory创建线程，任务由工作线程（Worker）执行。
     */


}
