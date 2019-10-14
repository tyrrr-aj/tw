package com.company;

public class Consumer implements Runnable {
    private Buffer buffer;
    private int numberOfMessages;

    public Consumer(Buffer buffer, int numberOfMessages) {
        this.buffer = buffer;
        this.numberOfMessages = numberOfMessages;
    }

    public void run() {

        for(int i = 0;  i < numberOfMessages;   i++) {
            String message = buffer.take();
        }

    }
}