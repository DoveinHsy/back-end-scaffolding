package org.xiaoxingbomei.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.entity.Dog;
import org.xiaoxingbomei.entity.Person;

import java.io.*;

@Service
@Slf4j
public class JavaServiceImpl
{

    /**
     * 值传递 & 引用传递
     */
    void swap(int a,int b)
    {
        int temp=a;
        a=b;
        b=temp;
    }

    void change(int x)
    {
        x = 10;
    }

    void change(Person person)
    {
        person.setName("changed");
    }

    /**
     * 每次方法调用，jvm都会在栈中创建一个栈帧，存局部变量
     * 调用这个方法的时候 person1 和 person2 就是栈帧中的局部变量，存的就是引用的副本
     * 当方法结束的时候，栈帧就会销毁了，不会影响原先的应用，所以java对象的传递看着像是引用传递 实际还是值传递 因为传递的是引用的副本
     * @param person1
     * @param person2
     */
    void changePerson(Person person1,Person person2)
    {
        Person temp = person1;
        person1 = person2;
        person2 = temp;
    }

    @Test
    public void JavaTransType()
    {
        int num = 5;
        change(num);
        System.out.println("基本数据类型值传递传的是副本，不会改变原先"+num);

        Person person1 = new Person();
        person1.setName("lvxianghe");
        change(person1);
        System.out.println("对象值传递传的是引用的副本，会改变原先值"+person1.getName());

        Person person2 = new Person();
        person2.setName("lvhexiang");
        changePerson(person1,person2);
        System.out.println("对象值传递传的是引用的副本，局部变量中改变的引用 不会影响原引用"+person1.getName()+person2.getName());
    }


    /**
     * 浅拷贝 - Cloneable
     */
    @Test
    public void copyOfCloneable()
    {
        Person person1 = new Person();
        person1.setName("吕相赫");
        Dog dog = new Dog();
        dog.setName("博美");
        person1.setDog(dog);
        Person person2 = null;
        try
        {
            person2 = (Person) person1.clone();
            person2.getDog().setName("大型博美");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        person2.setName("吕和翔");
        System.out.println("浅拷贝不会改变原先基本变量和引用字段，但是不会复制引用指向的对象"+person1.getName()+person1.getDog().getName()+person2.getName()+person2.getDog().getName());
    }


    /**
     * 浅拷贝 - 序列化
     */
    @Test
    public void deepCopyOfSerializable() throws Exception
    {
        Person person1 = new Person();
        person1.setName("lvxianghe");
        Dog dog = new Dog();
        dog.setName("博美");
        person1.setDog(dog);
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        //
        oos.writeObject(person1);
        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Person person2 = (Person)ois.readObject();

        person2.setName("lvhexiang");
        person2.getDog().setName("大型博美");
        System.out.println("深拷贝不会改变原先，已经是全新的对象了，包括引用的对象："+person1.getName()+person2.getName()+person1.getDog().getName()+person2.getDog().getName());

    }

    /**
     * 深拷贝 -
     */

    /**
     * 深拷贝 -
     */


    /**
     * ava 并发编程体系大纲（扁平化）
     * 1. 线程基础
     * 1.1 线程的创建方式
     * 继承 Thread 类
     * 实现 Runnable 接口
     * 实现 Callable 接口 + Future
     * 线程池方式创建
     * 1.2 线程的生命周期与状态转换
     * 1.3 线程的基本方法 (start()、join()、yield()、sleep()、interrupt())
     * 2. 线程同步
     * 2.1 synchronized 关键字
     * 2.2 volatile 关键字
     * 2.3 Lock 体系 (ReentrantLock, ReentrantReadWriteLock)
     * 2.4 ThreadLocal 线程变量
     * 2.5 CAS（Compare-And-Swap）和 Atomic 类
     * 2.6 ABA 问题与 AtomicStampedReference
     * 3. 线程间通信
     * 3.1 wait()、notify()、notifyAll()
     * 3.2 Condition 结合 Lock 使用
     * 3.3 生产者-消费者模型（阻塞队列）
     * 3.4 Thread.join() 和 CountDownLatch
     * 3.5 CyclicBarrier 和 Semaphore
     * 3.6 Exchanger 数据交换
     * 4. 线程池
     * 4.1 Executor 框架
     * 4.2 ThreadPoolExecutor 详细参数解析
     * 4.3 ScheduledThreadPoolExecutor
     * 4.4 线程池的拒绝策略
     * 4.5 线程池的动态调整（核心线程数、最大线程数）
     * 4.6 ForkJoinPool 详解
     * 5. Future 机制
     * 5.1 Future 和 FutureTask
     * 5.2 CompletableFuture 详解
     * 异步任务链
     * 异常处理
     * 组合多个任务
     * 5.3 CompletableFuture 和 Executor 结合使用
     * 6. 并发工具类
     * 6.1 BlockingQueue（ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue）
     * 6.2 ConcurrentHashMap 详解
     * 6.3 CopyOnWriteArrayList 和 CopyOnWriteArraySet
     * 6.4 ConcurrentLinkedQueue 和 LinkedBlockingQueue
     * 6.5 StampedLock
     * 7. JUC (java.util.concurrent) 详解
     * 7.1 LockSupport 详解
     * 7.2 AQS（AbstractQueuedSynchronizer）原理解析
     * 7.3 Unsafe 类的并发应用
     * 7.4 LongAdder 和 LongAccumulator
     * 8. 并发设计模式
     * 8.1 单例模式的并发安全实现
     * 8.2 生产者-消费者模式
     * 8.3 读写锁模式
     * 8.4 线程池管理模式
     * 8.5 Actor 并发模型
     * 8.6 Disruptor 高性能队列模型
     * 9. Java 内存模型（JMM）
     * 9.1 可见性、原子性、有序性
     * 9.2 Happens-Before 规则
     * 9.3 指令重排序
     * 9.4 volatile 的实现原理
     * 9.5 synchronized 的内存语义
     * 9.6 CAS 原理与底层实现
     * 10. 高级并发优化
     * 10.1 无锁编程与 Lock-Free 数据结构
     * 10.2 伪共享问题（False Sharing）
     * 10.3 线程上下文切换优化
     * 10.4 ForkJoinPool 的最佳实践
     * 10.5 Disruptor 高性能队列优化
     * 10.6 Netty 线程模型与 NIO
     */

    /**
     * 创建线程-继承Thread类
     * 原理：
     */

    @Test
    public void createThreadByThread()
    {

    }
    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            log.info("线程执行"+Thread.currentThread().getName());
        }
    }



























}
