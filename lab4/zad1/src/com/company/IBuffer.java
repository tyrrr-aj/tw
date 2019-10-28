package com.company;

public interface IBuffer<T> {
    T getDataAt(int index);
    void updateElementAt(int index, T newValue);
    int getNextIndex(int index);
    int size();
}
