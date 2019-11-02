package com.company;

import java.util.Random;

public class Consumer extends Thread {
    private ICountingBuffer buffer;
    private SeriesSummarizer summarizer;
    private int quantityLimit;

    public Consumer(ICountingBuffer buffer, SeriesSummarizer summarizer, int quantityLimit) {
        this.buffer = buffer;
        this.summarizer = summarizer;
        this.quantityLimit = quantityLimit;
    }

    public void run() {
        Random generator = new Random();
        long startTime, endTime;
        int quantity;
        Boolean shouldContinue = true;
        while (shouldContinue) {
            quantity = generator.nextInt(quantityLimit);
            startTime = System.nanoTime();
            shouldContinue = buffer.get(quantity);
            endTime = System.nanoTime();
            summarizer.addValue(quantity, endTime - startTime);
        }
    }
}
