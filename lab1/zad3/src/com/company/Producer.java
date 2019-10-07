package com.company;

public class Producer implements Runnable {
    private Buffer buffer;
    private int numberOfMessages;

    public Producer(Buffer buffer, int numberOfMessages) {
        this.buffer = buffer;
        this.numberOfMessages = numberOfMessages;
    }

    public void run() {

        for(int i = 0;  i < numberOfMessages;   i++) {
            buffer.put("message "+i);
        }

    }
}
