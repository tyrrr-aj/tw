package com.company;

import java.util.List;

public class Pipe<T> {
    private RoundedBuffer<T> buffer;
    private List<IProcessingUnit<T> > processingUnits; // first Processing Unit should be producer, last - consumer

    public Pipe(List<IProcessingUnit<T> > processingUnits, RoundedBuffer buffer) {
        this.processingUnits = processingUnits;
        this.buffer = buffer;
    }

    public void startProcessing() {
        for (IProcessingUnit<T> unit : processingUnits) {
            new ProcessingThread(unit, buffer).start();
        }
    }
}
