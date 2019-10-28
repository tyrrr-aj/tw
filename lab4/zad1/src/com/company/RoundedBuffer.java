package com.company;

import java.util.ArrayList;
import java.util.List;

public class RoundedBuffer<T> implements IBuffer<T>{
    private List<T> buffer;

    public RoundedBuffer(int length) {
        buffer = new ArrayList<>(length);
        while (buffer.size() < length) { buffer.add(null); }
    }

    public T getDataAt(int index) {
        return buffer.get(index);
    }

    public int getNextIndex(int i) {
        return (i + 1) % buffer.size();
    }

    public int size() {
        return buffer.size();
    }

    public void updateElementAt(int index, T newValue) {
        buffer.set(index, newValue);
    }
}
