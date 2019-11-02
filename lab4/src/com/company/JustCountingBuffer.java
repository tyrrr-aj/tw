package com.company;

public class JustCountingBuffer implements ICountingBuffer {
    private int size;
    private int numberOfElements;

    @Override
    public Boolean put(int quantity) {
        return false;
    }

    @Override
    public Boolean get(int quantity) {
        return false;
    }
}
