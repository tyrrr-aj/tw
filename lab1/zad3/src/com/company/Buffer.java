package com.company;

public class Buffer {
    private String message;
    private boolean isEmpty;

    public Buffer() {
        isEmpty = true;
    }

    public synchronized void put(String message) {
        try {
            while (!isEmpty) {
                wait();
            }
            this.message = message;
            isEmpty = false;
            notifyAll();
            System.out.println("Wpisano: " + message);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }

    public synchronized String take() {
        try {
            while (isEmpty) {
                wait();
            }
            isEmpty = true;
            notifyAll();
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
        System.out.println("Pobrano: " + message);
        return message;
    }
}
