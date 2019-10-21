package com.company;

public class Publisher extends Thread {
    private int ID;
    private int numberOfTasks;
    private PrinterMonitor printerMonitor;

    public Publisher(int ID, int numberOfTasks, PrinterMonitor printerMonitor) {
        this.ID = ID;
        this.numberOfTasks = numberOfTasks;
        this.printerMonitor = printerMonitor;
    }

    public void run() {
        for (int i = 0; i < numberOfTasks; i++) {
            preparePrintingTask();
            Printer currentPrinter = printerMonitor.reserve();
            System.out.printf("[%d] Publisher %d: printing on printer %d%n", System.currentTimeMillis(), ID, currentPrinter.getID());
            currentPrinter.print();
            System.out.printf("[%d] Publisher %d: finished printing printing on printer %d%n", System.currentTimeMillis(), ID, currentPrinter.getID());
            printerMonitor.free(currentPrinter);
        }
    }

    private void preparePrintingTask() {
        try {
            sleep((ID % 3 + 1) * 1000);
        }
        catch (InterruptedException e) {};
    }
}
