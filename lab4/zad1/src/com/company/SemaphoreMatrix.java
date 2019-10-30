package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreMatrix implements ISemaphorsProvider {
    private List <List <Semaphore> > matrix;

    public SemaphoreMatrix(int bufferLength, int numberOfProcessingUnits) {
        matrix = new ArrayList<>();
        matrix.add(initRow(bufferLength, 1));
        for (int i = 1; i < numberOfProcessingUnits; i++) {
            matrix.add(initRow(bufferLength, 0));
        }
    }

    private List<Semaphore> initRow(int length, int permits) {
        List<Semaphore> row = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            row.add(new Semaphore(permits));
        }
        return row;
    }

    public Semaphore getMySemaphore(int processingUnitIndex, int elementIndex) {
        return matrix.get(processingUnitIndex).get(elementIndex);
    }

    public Semaphore getSuccessorSemaphore(int processingUnitIndex, int elementIndex) {
        return matrix.get(getSuccessorID(processingUnitIndex)).get(elementIndex);
    }

    private int getSuccessorID(int ID) {
        return (ID + 1) % matrix.size();
    }
}
