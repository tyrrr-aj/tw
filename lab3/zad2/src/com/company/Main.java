package com.company;

public class Main {

    public static void main(String[] args) {
	    Waiter waiter = new Waiter();

	    for (int i = 0; i < 10; i++) {
	        Person firstPerson = new Person(2*i, 2*i + 1, waiter);
            Person secondPerson = new Person(2*i + 1, 2*i, waiter);

            firstPerson.start();
            secondPerson.start();
        }
    }
}
