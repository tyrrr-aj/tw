package com.company;

import java.util.List;

public class Pipe<T> {
    private IBuffer<T> buffer;
    private List<IProcessingUnit<T> > processingUnits; // first Processing Unit should be producer, last - consumer
    private SemaphoreMatrix semaphoreMatrix;

    public Pipe(List<IProcessingUnit<T> > processingUnits, IBuffer<T> buffer) {
        this.processingUnits = processingUnits;
        this.buffer = buffer;
        semaphoreMatrix = new SemaphoreMatrix(buffer.size(), processingUnits.size());
    }

    public void startProcessing() {
        for (int i = 0; i < processingUnits.size(); i++) {
            new ProcessingThread(processingUnits.get(i), buffer, semaphoreMatrix, i).start();
        }
    }
}
