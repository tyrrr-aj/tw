package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop(5);
        ArrayList<Thread> customerThreads = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Customer customer = new Customer(i, shop);
            customerThreads.add(new Thread(customer));
        }

        for (Thread thread : customerThreads) {
            thread.start();
        }
    }
}
