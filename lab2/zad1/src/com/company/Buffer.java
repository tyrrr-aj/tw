package com.company;

public class Buffer {
    private String message;
    private BinarySemaphore semaphorePUT;
    private BinarySemaphore semaphoreTAKE;

    public Buffer() {
        this.semaphorePUT = new BinarySemaphore(false);
        this.semaphoreTAKE = new BinarySemaphore(true);
    }

    public void put(String message) {
        semaphorePUT.P();
        this.message = message;
        System.out.println("Wpisano: " + message);
        try {
            semaphoreTAKE.V();
        }
        catch (SemaphoreCorruptedException e) {
            System.out.println("ERROR: Binary semaphore (TAKE) lifted twice");
        }
    }

    public String take() {
        semaphoreTAKE.P();
        System.out.println("Pobrano: " + message);
        try {
            semaphorePUT.V();
        }
        catch (SemaphoreCorruptedException e) {
            System.out.println("ERROR: Binary semaphore (PUT) lifted twice");
        }
        return message;
    }
}
