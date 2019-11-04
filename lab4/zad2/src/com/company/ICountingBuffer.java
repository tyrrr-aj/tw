package com.company;

import java.util.Set;

public interface ICountingBuffer {
    Boolean put(int quantity);
    Boolean get(int quantity);
}
