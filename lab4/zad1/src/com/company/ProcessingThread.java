package com.company;

class ProcessingThread<T> extends Thread {
    private IProcessingUnit<T> processingUnit;
    private IBuffer<T> buffer;
    private SemaphoreMatrix semaphoreMatrix;
    private int currentElementIndex;
    private int myID;

    public ProcessingThread(IProcessingUnit<T> processingUnit, IBuffer<T> buffer, int ID) {
        this.processingUnit = processingUnit;
        this.buffer = buffer;
        currentElementIndex = 0;
        this.myID = ID;
    }

    public void run() {
        while (true) {
            try {
                buffer.acquireMySemaphore(myID, currentElementIndex);
                buffer.updateElementAt(currentElementIndex, processingUnit.process(buffer.getDataAt(currentElementIndex)));
                System.out.printf("[%d] Unit %d: index %d, value %d%n",
                        System.currentTimeMillis(), myID, currentElementIndex, buffer.getDataAt(currentElementIndex));
                buffer.releaseSuccessorSemaphore(myID, currentElementIndex);
                currentElementIndex = buffer.getNextIndex(currentElementIndex);
            }
            catch (InterruptedException ignore) { }
        }
    }
}
