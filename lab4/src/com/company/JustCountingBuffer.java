package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.math3.distribution.NormalDistribution;

public class JustCountingBuffer implements ICountingBuffer {
    private int size;
    private int numberOfElements = 0;

    private long timeLimit;
    private long startTime;

    private final Lock lock = new ReentrantLock();
    private final Condition itemsPut = lock.newCondition();
    private final Condition itemsRemoved = lock.newCondition();

    public JustCountingBuffer(int size, long timeLimit) {
        this.size = size;
        this.timeLimit = timeLimit;
        startTime = System.nanoTime();
        distribution = new NormalDistribution((double) size/2, 0.3 * size); // values computed in odchylenie_standardowe_pokrycia_bufora.R script
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
