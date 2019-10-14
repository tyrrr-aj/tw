package com.company;

public class CountingSemaphore {
    private int value;

    public CountingSemaphore(int value) {
        if (value >= 0)
            this.value = value;
        else
            throw(new IllegalArgumentException());
    }

    public synchronized void P() {
        try {
            while (value == 0) {
                wait();
            }
            value--;
        }
        catch (InterruptedException e) {
            System.out.println("ERROR: Waiting on semaphore has been interrupted!");
        }
    }

    public synchronized void V() {
        value++;
        notify();
    }
}
