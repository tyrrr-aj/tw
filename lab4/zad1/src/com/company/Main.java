package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int numberOfProcessingUnits = 7;    // including producer and consumer
        int bufferLength = 100;

        List<IProcessingUnit<Integer> > pipeline = new ArrayList<>(numberOfProcessingUnits);

        pipeline.add(new Producer());
        for (int i = 0; i < numberOfProcessingUnits - 2; i++) {     // -2 for producer and consumer
            pipeline.add(new Incrementor());
        }
        pipeline.add(new Consumer());

        Pipe<Integer> pipe = new Pipe<>(pipeline, new RoundedBuffer<>(bufferLength,
                                                    new SemaphoreMatrix(bufferLength, numberOfProcessingUnits)));

        pipe.startProcessing();
    }
}
