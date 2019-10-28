package com.company;

class ProcessingThread<T> extends Thread {
    private IProcessingUnit<T> processingUnit;
    private IBuffer<T> buffer;
    private SemaphoreMatrix semaphoreMatrix;
    private int currentElementIndex;
    private int myID;

    public ProcessingThread(IProcessingUnit<T> processingUnit, IBuffer<T> buffer, SemaphoreMatrix semaphoreMatrix, int ID) {
        this.processingUnit = processingUnit;
        this.buffer = buffer;
        currentElementIndex = 0;
        this.semaphoreMatrix = semaphoreMatrix;
        this.myID = ID;
    }

    public void run() {
        while (true) {
            try {
                semaphoreMatrix.getSemaphore(currentElementIndex, myID).acquire();
                buffer.updateElementAt(currentElementIndex, processingUnit.process(buffer.getDataAt(currentElementIndex)));
                System.out.printf("[%d] Unit %d: index %d, value %d%n",
                        System.currentTimeMillis(), myID, currentElementIndex, buffer.getDataAt(currentElementIndex));
                semaphoreMatrix.getSemaphore(currentElementIndex, semaphoreMatrix.getSuccessorID(myID)).release();
                currentElementIndex = buffer.getNextIndex(currentElementIndex);
            }
            catch (InterruptedException ignore) { }
        }
    }
}
