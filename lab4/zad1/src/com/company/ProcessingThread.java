package com.company;

import java.util.List;
import java.util.concurrent.Semaphore;

class ProcessingThread<T> extends Thread {
    private IProcessingUnit<T> processingUnit;
    private RoundedBuffer<T> buffer;
    private List<Semaphore> mySemaphores;
    private List<Semaphore> proceedorSemaphores;
    private int currentElementIndex;

    public ProcessingThread(IProcessingUnit<T> processingUnit, RoundedBuffer buffer) {
        this.processingUnit = processingUnit;
        this.buffer = buffer;
        currentElementIndex = 0;
    }

    public void run() {
        while (true) {
            try {
                mySemaphores.get(currentElementIndex).acquire();
                // TODO modify apropriate element, release appropriate semaphore
                currentElementIndex = buffer.getNextIndex(currentElementIndex);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
