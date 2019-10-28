package com.company;

import java.util.Random;

public class Consumer implements IProcessingUnit<Integer> {
    @Override
    public Integer process(Integer input) {
        Random generator = new Random();
        try {
            Thread.sleep(generator.nextInt(3) * 1000);
        }
        catch (InterruptedException ignore) { }
        return -1;
    }

}