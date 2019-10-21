package com.company;

public class Person extends Thread {
    private int ID;
    private int pairedPersonID;
    private Waiter waiter;

    public Person(int ID, int pairedPersonID, Waiter waiter) {
        this.ID = ID;
        this.pairedPersonID = pairedPersonID;
        this.waiter = waiter;
    }

    public void run() {
        runErrands();
        waiter.getTable(ID, pairedPersonID);
        eat();
        waiter.leaveTable(ID, pairedPersonID);
    }

    private void runErrands() {
        try {
            sleep(((ID % 3) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        try {
            sleep((((ID + 2) % 3) + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
