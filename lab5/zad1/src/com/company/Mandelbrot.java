package com.company;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;

    public Mandelbrot(int numberOfThreads, int numberOfTasks) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        Set<Future<Map<Coordinates, Integer>>> resultSet = new HashSet<>();
        for (int i = 0; i < numberOfTasks; i++) {
            Coordinates leftUpper = new Coordinates(getWidth() / numberOfTasks * i, getHeight() / numberOfTasks * i);
            Coordinates rightLower = new Coordinates(getWidth() / numberOfTasks * (i + 1), getHeight() / numberOfTasks * (i + 1));
            Callable<Map<Coordinates, Integer>> callable = new MandlebortSquareCallable(leftUpper, rightLower, MAX_ITER, ZOOM);
            Future<Map<Coordinates, Integer>> result = pool.submit(callable);
            resultSet.add(result);
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
        new Mandelbrot(1, 1).setVisible(true);
    }
}