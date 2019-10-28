package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<IProcessingUnit<Integer> > pipeline = new ArrayList<>();

        pipeline.add(new Producer());
        for (int i = 0; i < 5; i++) {
            pipeline.add(new Incrementor());
        }
        pipeline.add(new Consumer());

        Pipe<Integer> pipe = new Pipe<>(pipeline, new RoundedBuffer<>(100));

        pipe.startProcessing();
    }
}
