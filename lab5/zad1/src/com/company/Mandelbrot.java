package com.company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 600;
    private final double ZOOM = 200;
    private BufferedImage I;

    public Mandelbrot(int numberOfThreads, int numberOfTasksHorizontal, int numberOfTasksVertical) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        Set<Future<Map<Coordinates, Integer>>> resultSet = new HashSet<>();
        for (int i = 0; i < numberOfTasksHorizontal; i++) {
            for (int j = 0; j < numberOfTasksVertical; j++) {
                Coordinates leftUpper = new Coordinates(getWidth() / numberOfTasksHorizontal * i, getHeight() / numberOfTasksVertical * j);
                Coordinates rightLower = new Coordinates(getWidth() / numberOfTasksHorizontal * (i + 1), getHeight() / numberOfTasksVertical * (j + 1));
                Callable<Map<Coordinates, Integer>> callable = new MandlebortPartCallable(leftUpper, rightLower, MAX_ITER, ZOOM);
                Future<Map<Coordinates, Integer>> result = pool.submit(callable);
                resultSet.add(result);
            }
        }
        for (Future<Map<Coordinates, Integer>> result : resultSet) {
            try {
                for (Map.Entry<Coordinates, Integer> pixelResult : result.get().entrySet()) {
                    I.setRGB(pixelResult.getKey().X, pixelResult.getKey().Y, pixelResult.getValue() | (pixelResult.getValue() << 8));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        List<Integer> testedNumbersOfThreads = new ArrayList<>(List.of(1, 4, 8));
        try {
            CSVPrinter printer = new CSVPrinter(new FileWriter("results.csv"), CSVFormat.EXCEL);
            printer.printRecord("liczba wątków\\liczba zadań", "tyle co wątków", "10 * liczba wątków", "tyle co pikseli");
            for (int numberOfThreads : testedNumbersOfThreads) {
                System.out.printf("numberOfThreads %d started%n", numberOfThreads);
                printer.printRecord(
                        numberOfThreads,
                        measureMandelbrot(numberOfThreads, numberOfThreads, 1),
                        measureMandelbrot(numberOfThreads, numberOfThreads, 10),
                        measureMandelbrot(numberOfThreads, 800, 600)
                );
                printer.flush();
                System.out.printf("%nnumberOfThreads %d done%n", numberOfThreads);
            }
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double measureMandelbrot(int numberOfThreads, int numberOfTasksHorizontal, int numberOfTasksVertical) {
        final long timeUnitsPerSecond = 1000000000;
        long startTime, endTime;
        List<Long> results = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            new Mandelbrot(numberOfThreads, numberOfTasksHorizontal, numberOfTasksVertical).setVisible(false);
            endTime = System.nanoTime();
            results.add(endTime - startTime);
            System.out.print(".");
        }
        return Math.round(results.stream().mapToDouble(a -> a).average().getAsDouble() / timeUnitsPerSecond * 100) / 100.0;
    }
}