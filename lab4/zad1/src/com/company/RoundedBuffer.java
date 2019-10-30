package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class RoundedBuffer<T> implements IBuffer<T>{
    private List<T> buffer;
    private ISemaphorsProvider semaphoreProvider;

    public RoundedBuffer(int length, ISemaphorsProvider semaphoreProvider) {
        buffer = new ArrayList<>(length);
        while (buffer.size() < length) { buffer.add(null); }
        this.semaphoreProvider = semaphoreProvider;
    }

    public T getDataAt(int index) {
        return buffer.get(index);
    }

    public int getNextIndex(int i) {
        return (i + 1) % buffer.size();
    }

    public void updateElementAt(int index, T newValue) {
        buffer.set(index, newValue);
    }

    public void acquireMySemaphore(int ID, int elementIndex) throws InterruptedException {
        semaphoreProvider.getMySemaphore(ID, elementIndex).acquire();
    }

    public void releaseSuccessorSemaphore(int ID, int elementIndex) {
        semaphoreProvider.getSuccessorSemaphore(ID, elementIndex).release();
    }
}
