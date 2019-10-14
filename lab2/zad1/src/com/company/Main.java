package com.company;

public class Main {

    public static void main(String[] args) {
	    Buffer buffer = new Buffer();
	    for (int i = 0; i < 5; i++) {
	        Producer producer = new Producer(i, buffer, 10);
	        Consumer consumer = new Consumer(buffer, 10);

	        Thread producerThread = new Thread(producer);
	        Thread consumerThread = new Thread(consumer);

	        producerThread.start();
	        consumerThread.start();
        }
    }
}
