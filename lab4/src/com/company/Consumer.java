package com.company;

import java.util.Random;

public class Consumer extends Thread {
    private ICountingBuffer buffer;
    private SeriesSummarizer summarizer;
    private long timeout;
    private int quantityLimit;

    public Consumer(ICountingBuffer buffer, SeriesSummarizer summarizer, long timeout, int quantityLimit) {
        this.buffer = buffer;
        this.summarizer = summarizer;
        this.timeout = timeout;
        this.quantityLimit = quantityLimit;
    }

    public void run() {
        Random generator = new Random();
        long runMethodInvocationTime = System.nanoTime();
        while (System.nanoTime() - runMethodInvocationTime < timeout) {
            int quantity = generator.nextInt(quantityLimit);
            long startTime = System.nanoTime();
            buffer.get(quantity);
            long endTime = System.nanoTime();
            summarizer.addValue(quantity, endTime - startTime);
        }
    }
}
