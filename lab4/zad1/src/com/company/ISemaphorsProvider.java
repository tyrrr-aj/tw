package com.company;

import java.util.concurrent.Semaphore;

public interface ISemaphorsProvider {
    Semaphore getMySemaphore(int myID, int elementIndex);
    Semaphore getSuccessorSemaphore(int myID, int elementIndex);
}
