package com.company;

public class Producer implements Runnable {
    private int ID;
    private Buffer buffer;
    private int numberOfMessages;

    public Producer(int ID, Buffer buffer, int numberOfMessages) {
        this.ID = ID;
        this.buffer = buffer;
        this.numberOfMessages = numberOfMessages;
    }

    public void run() {

        for(int i = 0;  i < numberOfMessages;   i++) {
            buffer.put("Producer " + ID + " message " + i);
        }

    }
}
