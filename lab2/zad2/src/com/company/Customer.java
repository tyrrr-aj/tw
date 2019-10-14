package com.company;

import static java.lang.Thread.sleep;

public class Customer implements Runnable {
    private int ID;
    Shop favouriteShop;

    public Customer(int ID, Shop favouriteShop) {
        this.ID = ID;
        this.favouriteShop = favouriteShop;
    }

    @Override
    public void run() {
        favouriteShop.enterShop(ID);
        try {
            sleep((ID % 3 + 1) * 1000);
        }
        catch (InterruptedException e) {
            System.out.printf("ERROR: Customer {}: somebody interrupted me while shopping!\n", ID);
        }
        favouriteShop.checkout(ID);
    }
}
