package com.company;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    final private Lock lock = new ReentrantLock();
    final private Condition printerIsAvilable = lock.newCondition();

    private Queue<Printer> avilablePrinters;

    public PrinterMonitor(Queue<Printer> printers) {
        this.avilablePrinters = printers;
    }

    public Printer reserve() {
        lock.lock();
        while (avilablePrinters.size() == 0) {
            try {
                printerIsAvilable.await();
            }
            catch (InterruptedException e) {};
        }
        Printer grantedPrinter = avilablePrinters.remove();
        lock.unlock();
        return grantedPrinter;
    }

    public void free(Printer printer) {
        lock.lock();
        if (avilablePrinters.size() == 0)
            printerIsAvilable.signal();
        avilablePrinters.add(printer);
        lock.unlock();
    }
}
