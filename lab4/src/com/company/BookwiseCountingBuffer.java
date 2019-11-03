package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BookwiseCountingBuffer implements ICountingBuffer {
    private int size;
    private int numberOfElements = 0;
    private long timeLimit;
    private long startTime;

    private final Lock lock = new ReentrantLock();
    private final Condition firstProducer = lock.newCondition();
    private final Condition firstConsumer = lock.newCondition();
    private final Condition restOfProducers = lock.newCondition();
    private final Condition restOfConsumers = lock.newCondition();

    private Boolean isFirstProducerSelected = false;
    private Boolean isFirstConsumerSelected = false;

    public BookwiseCountingBuffer(int size, long timeLimit) {
        this.size = size;
        this.timeLimit = timeLimit;
        startTime = System.nanoTime();
    }

    @Override
    public Boolean put(int quantity) {
        lock.lock();
        try {
            long nanos = startTime + timeLimit - System.nanoTime();
            while (isFirstProducerSelected) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = restOfProducers.awaitNanos(nanos);
            }
            isFirstProducerSelected = true;
            while (size - numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = firstProducer.awaitNanos(nanos);
            }
            numberOfElements += quantity;
            isFirstProducerSelected = false;
            restOfProducers.signal();
            firstConsumer.signal();
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
            while (isFirstConsumerSelected) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = restOfConsumers.awaitNanos(nanos);
            }
            isFirstConsumerSelected = true;
            while (numberOfElements < quantity) {
                if (nanos <= 0L) {
                    return false;
                }
                nanos = firstConsumer.awaitNanos(nanos);
            }
            numberOfElements -= quantity;
            isFirstConsumerSelected = false;
            restOfConsumers.signal();
            firstProducer.signal();
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
