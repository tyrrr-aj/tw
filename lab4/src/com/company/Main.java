package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long timeout = 1000000000;

        Plotter naiveProducersPlotter = new Plotter("Naive: Producers");
        Plotter naiveConsumersPlotter = new Plotter("Naive: Consumers");

        Plotter justProducersPlotter = new Plotter("Just: Producers");
        Plotter justConsumersPlotter = new Plotter("Just: Consumers");

        for (int bufferSize = 1000; bufferSize <= 100000; bufferSize *= 10) {
            for (int PCNumber = 10; PCNumber <= 1000; PCNumber *= 10) { // PCNumber - number of Producers and Consumers

                ICountingBuffer buffer = new NaiveCountingBuffer(bufferSize, timeout);

                SeriesSummarizer producersSummarizer = new SeriesSummarizer(bufferSize);
                SeriesSummarizer consumersSummarizer = new SeriesSummarizer(bufferSize);

                List<Thread> producersAndConsumers = new ArrayList<>(PCNumber * 2);

                for (int i = 0; i < PCNumber; i++) {
                    producersAndConsumers.add(new Producer(buffer, producersSummarizer, bufferSize / 2));
                    producersAndConsumers.add(new Consumer(buffer, consumersSummarizer, bufferSize / 2));
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

        for (int bufferSize = 1000; bufferSize <= 100000; bufferSize *= 10) {
            for (int PCNumber = 10; PCNumber <= 1000; PCNumber *= 10) { // PCNumber - number of Producers and Consumers

                ICountingBuffer buffer = new JustCountingBuffer(bufferSize, timeout);

                SeriesSummarizer producersSummarizer = new SeriesSummarizer(bufferSize);
                SeriesSummarizer consumersSummarizer = new SeriesSummarizer(bufferSize);

                List<Thread> producersAndConsumers = new ArrayList<>(PCNumber * 2);

                for (int i = 0; i < PCNumber; i++) {
                    producersAndConsumers.add(new Producer(buffer, producersSummarizer, bufferSize / 2));
                    producersAndConsumers.add(new Consumer(buffer, consumersSummarizer, bufferSize / 2));
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
                justProducersPlotter.addSeries(
                        String.format("bufferSize %d, Producers and Consumers %d", bufferSize, PCNumber),
                        producersSummarizer.getMeans()
                );
                justConsumersPlotter.addSeries(
                        String.format("bufferSize %d, Producers and Consumers %d", bufferSize, PCNumber),
                        consumersSummarizer.getMeans()
                );
                System.out.printf("Just: buffer %d, PC %d tested%n", bufferSize, PCNumber);
            }
        }

        naiveProducersPlotter.plot();
        naiveConsumersPlotter.plot();
        justProducersPlotter.plot();
        justConsumersPlotter.plot();

    }
}
