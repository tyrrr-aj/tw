package com.company;

public interface IBuffer<T> {
    T getDataAt(int index);
    void updateElementAt(int index, T newValue);
    int getNextIndex(int index);
    void acquireMySemaphore(int myID, int index) throws InterruptedException;
    void releaseSuccessorSemaphore(int myID, int index);
}
