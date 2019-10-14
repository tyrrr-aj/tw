package com.company;

public class Shop {
    CountingSemaphore bucketsSemaphore;

    public Shop(int numberOfBuckets) {
        this.bucketsSemaphore = new CountingSemaphore(numberOfBuckets);
        System.out.printf("Shop capacity: %d%n", numberOfBuckets);
    }

    public void enterShop(int customerID) {
        bucketsSemaphore.P();
        System.out.printf("[%d] Customer %d: entering shop%n", System.currentTimeMillis() % 10000, customerID);
    }

    public void checkout(int customerID) {
        bucketsSemaphore.V();
        System.out.printf("[%d] Customer %d: leaving shop%n", System.currentTimeMillis() % 10000, customerID);
    }
}
