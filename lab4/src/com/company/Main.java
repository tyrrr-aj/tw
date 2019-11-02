package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long timeout = 1000;

        Plotter naiveProducersPlotter = new Plotter("Naive: Producers");
        Plotter naiveConsumersPlotter = new Plotter("Naive: Consumers");

        for (int bufferSize = 1000; bufferSize <= 100000; bufferSize *= 10) {
            for (int PCNumber = 10; PCNumber <= 1000; PCNumber *= 10) { // PCNumber - number of Producers and Consumers
                ICountingBuffer buffer = new NaiveCountingBuffer(bufferSize);
                SeriesSummarizer producersSummarizer = new SeriesSummarizer();
                SeriesSummarizer consumersSummarizer = new SeriesSummarizer();
                List<Thread> producersAndConsumers = new ArrayList<>(PCNumber * 2);
                for (int i = 0; i < PCNumber; i++) {
                    producersAndConsumers.add(new Producer(buffer, producersSummarizer, timeout, bufferSize / 2));
                    producersAndConsumers.add(new Consumer(buffer, consumersSummarizer, timeout, bufferSize / 2));
                }
                for (Thread PC : producersAndConsumers) {
                    PC.start();
                }
                try {
                    for (Thread PC : producersAndConsumers) {
                        PC.join();
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                naiveProducersPlotter.addSeries(
                        String.format("bufferSize %d, Producers and Consumers %d", bufferSize, PCNumber),
                        producersSummarizer.getMeans()
                );
                naiveConsumersPlotter.addSeries(
                        String.format("bufferSize %d, Producers and Consumers %d", bufferSize, PCNumber),
                        consumersSummarizer.getMeans()
                        );
                System.out.printf("Naive: buffer %d, PC %d tested%n", bufferSize, PCNumber);
            }
        }

        naiveProducersPlotter.plot();
        naiveConsumersPlotter.plot();
    }

}
