package com.company;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Selector {
    private Lock lock;
    private Map<Double, Condition> threads = new HashMap<>();
    private NormalDistribution distribution;
    private Random generator = new Random();
    private double sum = 0;

    public Selector(Lock lock, NormalDistribution distribution) {
        this.lock = lock;
        this.distribution = distribution;
    }

    public synchronized Condition add(int quantity) {
        double probability = nonSuccessfullProbability(quantity);
        Condition condition = lock.newCondition();
        sum += probability;
        threads.put(sum, condition);
        return condition;
    }

    public synchronized void signalOne() {
        double randomValue = generator.nextDouble() * sum;
        try {
            Map.Entry<Double, Condition> chosenEntry = threads.entrySet().stream()
                    .filter(e -> e.getKey() >= randomValue)
                    .min(Comparator.comparing(Map.Entry::getKey)).orElseThrow(NoSuchElementException::new);
            chosenEntry.getValue().signal();
            sum -= chosenEntry.getKey();
            threads.remove(chosenEntry.getKey());
        }
        catch (NoSuchElementException ignore) {}
    }

    private double nonSuccessfullProbability(int desiredQuantity) {
        return distribution.cumulativeProbability(desiredQuantity);
    }
}
