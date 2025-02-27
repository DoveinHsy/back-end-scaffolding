package org.xiaoxingbomei.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class DynamicLinkedBlockingQueue<E> extends LinkedBlockingQueue<E>
{

    private volatile int capacity;

    public DynamicLinkedBlockingQueue(int capacity)
    {
        super(capacity);
        this.capacity = capacity;
    }

    public void adjustCapacity(int newCapacity)
    {
        if (newCapacity < this.size())
        {
            log.warn("New capacity is less than current queue size. Some tasks may be lost.");
        }
        // 调整队列的容量
        setCapacity(newCapacity);
        log.info("Queue capacity adjusted to {}", newCapacity);
    }

    public synchronized void setCapacity(int newCapacity)
    {
        // 这里可以控制队列的容量变更逻辑
        synchronized (this)
        {
            if(newCapacity!=capacity)
            {
                this.capacity = newCapacity;
                log.info("New capacity changed to {}", newCapacity);
            }
        }
        // 例如通过反射或者其他方式修改队列的容量
        // 这需要根据具体的实现进行细化
    }

    public int getCapacity()
    {
        return capacity;
    }
}