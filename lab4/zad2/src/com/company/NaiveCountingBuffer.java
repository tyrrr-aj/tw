package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveCountingBuffer implements ICountingBuffer{
    private int size;
    private int numberOfElements = 0;
    private long timeLimit;
    private long startTime;

    private final Lock lock = new ReentrantLock();
    private final Condition itemsPut = lock.newCondition();
    private final Condition itemsRemoved = lock.newCondition();

    public NaiveCountingBuffer(int size, long timeLimit) {
        this.size = size;
        this.timeLimit = timeLimit;
        startTime = System.nanoTime();
    }

    @Override
    public Boolean put(int quantity) {
        lock.lock();
        try {
            long nanos = startTime + timeLimit - System.nanoTime();
            while (size - numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = itemsPut.awaitNanos(nanos);
            }
            numberOfElements += quantity;
            itemsPut.signalAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
        return true;
    }

    @Override
    public Boolean get(int quantity) {
        lock.lock();
        try {
            long nanos = startTime + timeLimit - System.nanoTime();
            while (numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = itemsPut.awaitNanos(nanos);
            }
            numberOfElements -= quantity;
            itemsRemoved.signalAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
        return true;
    }
}
