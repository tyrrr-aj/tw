package com.company;

import java.util.Random;

public class Incrementor implements IProcessingUnit<Integer> {
    @Override
    public Integer process(Integer input) {
        /*Random generator = new Random();
        try {
            Thread.sleep(generator.nextInt(2) * 1000);
        }
        catch (InterruptedException ignore) { } */
        return input + 1;
    }

}
