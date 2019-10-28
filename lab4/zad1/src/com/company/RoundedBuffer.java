package com.company;

import java.util.ArrayList;
import java.util.List;

public class RoundedBuffer<T> {
    private List<T> buffer;

    public RoundedBuffer() {
        buffer = new ArrayList<>();
    }

    public T getDataAt(int index) {
        return buffer.get(index);
    }

    public int getNextIndex(int i) {
        return (i + 1) % buffer.size();
    }
}
