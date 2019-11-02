package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveCountingBuffer implements ICountingBuffer{
    private int size;
    private int numberOfElements;

    private final Lock lock = new ReentrantLock();
    private final Condition itemsPut = lock.newCondition();
    private final Condition itemsRemoved = lock.newCondition();

    public NaiveCountingBuffer(int size) {
        this.size = size;
        numberOfElements = 0;
    }

    public void put(int quantity) {
        lock.lock();
        try {
            while (size - numberOfElements < quantity) {
                itemsRemoved.await();
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
    }

    public void get(int quantity) {
        lock.lock();
        try {
            while (numberOfElements < quantity) {
                itemsPut.await();
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
    }
}
