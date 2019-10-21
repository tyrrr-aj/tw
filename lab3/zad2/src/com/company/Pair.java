package com.company;

public class Pair<TKey, TValue> {
    public TKey key;
    public TValue value;

    public Pair(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }
}
