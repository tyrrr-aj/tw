package com.company;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        Queue<Printer> printers = new ArrayDeque<>();
        for (int i = 0; i < 3; i++) {
            printers.add(new Printer(i));
        }

        PrinterMonitor printerMonitor = new PrinterMonitor(printers);

        for (int i = 0; i < 7; i++) {
            Publisher publisher = new Publisher(i, 3, printerMonitor);
            publisher.start();
        }
    }
}
