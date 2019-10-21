package com.company;

import static java.lang.Thread.sleep;

public class Printer {
    private int ID;

    public Printer(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void print() {
        try {
            sleep((ID % 3 + 1) * 1000);
        }
        catch (InterruptedException e) {
            System.out.printf("[%d] Printer %d: printing interrupted%n", System.currentTimeMillis(), ID);
        }
    }
}
