package com.company;

public class BinarySemaphore {
    private Boolean isTaken;

    public BinarySemaphore(Boolean isInitiallyTaken) {
        this.isTaken = isInitiallyTaken;
    }

    public synchronized void P() {
        try {
            while (this.isTaken) {
                wait();
            }
            isTaken = true;
        }
        catch (InterruptedException e) {
            System.out.println("Semaphore interrupted!" + e);
        }
    }

    public synchronized void V() throws SemaphoreCorruptedException {
        if (!isTaken) {
            throw new SemaphoreCorruptedException();
        }
        else {
            isTaken = false;
            notify();
        }
    }
}
