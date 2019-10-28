package com.company;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    final private Lock tableLock = new ReentrantLock();
    final private Condition tableFree = tableLock.newCondition();

    private Set<Pair<Integer, Condition> > waitingSingles; // condition - "has partner arrived?"
    private int numberOfPeopleAtTable;

    public Waiter() {
        waitingSingles = new HashSet<>();
        numberOfPeopleAtTable = 0;
    }

    public void getTable(int newPersonID, int pairedID) {
        tableLock.lock();
        for (Pair<Integer, Condition> pair : waitingSingles) {
            if (pair.key.equals(pairedID)) {
                try {
                    while (numberOfPeopleAtTable > 0) {
                        tableFree.await();
                    }
                    pair.value.signal();
                    waitingSingles.remove(pair);
                    System.out.printf("[%d] Person %d (pair: %d) has been seated%n", System.currentTimeMillis() % 10000000, newPersonID, pairedID);
                    numberOfPeopleAtTable++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    tableLock.unlock();
                    return;
                }
            }
        }
        Condition waitingPlace = tableLock.newCondition();
        waitingSingles.add(new Pair<>(newPersonID, waitingPlace));
        try {
            waitingPlace.await();
            System.out.printf("[%d] Person %d (pair: %d) has been seated%n", System.currentTimeMillis() % 10000000, newPersonID, pairedID);
            numberOfPeopleAtTable++;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            tableLock.unlock();
        }
    }

    public void leaveTable(int leavingPersonID, int pairedID) {
        tableLock.lock();
        System.out.printf("[%d] Person %d (pair: %d) has left%n", System.currentTimeMillis() % 10000000, leavingPersonID, pairedID);
        numberOfPeopleAtTable--;
        if (numberOfPeopleAtTable == 0)
            tableFree.signal();
        tableLock.unlock();
    }
}
