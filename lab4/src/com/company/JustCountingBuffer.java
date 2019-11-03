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
    private Selector producerSelector;
    private Selector consumerSelector;

    public JustCountingBuffer(int size, long timeLimit) {
        this.size = size;
        this.timeLimit = timeLimit;
        startTime = System.nanoTime();
        producerSelector = new Selector(lock, new NormalDistribution((double) size/2, 0.3 * size)); // values computed in odchylenie_standardowe_pokrycia_bufora.R script
        consumerSelector = new Selector(lock, new NormalDistribution((double) size/2, 0.3 * size));
    }

    @Override
    public Boolean put(int quantity) {
        Condition mayTryToPut;
        lock.lock();
        try {
            long nanos = startTime + timeLimit - System.nanoTime();
            while (size - numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                mayTryToPut = producerSelector.add(quantity);
                nanos = mayTryToPut.awaitNanos(nanos);
            }
            numberOfElements += quantity;
            producerSelector.signalOne();
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
        Condition mayTryToGet;
        lock.lock();
        try {
            long nanos = startTime + timeLimit - System.nanoTime();
            while (numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                mayTryToGet = consumerSelector.add(quantity);
                nanos = mayTryToGet.awaitNanos(nanos);
            }
            numberOfElements -= quantity;
            consumerSelector.signalOne();
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
