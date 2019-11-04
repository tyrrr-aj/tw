package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MandlebortSquareCallable implements Callable<Map<Coordinates, Integer>> {
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int MAX_ITER;
    private double ZOOM;

    public MandlebortSquareCallable(Coordinates leftUpper, Coordinates rightLower, int MAX_ITER, double ZOOM) {
        xMin = leftUpper.X;
        yMin = leftUpper.Y;
        xMax = rightLower.X;
        yMax = rightLower.Y;
        this.MAX_ITER = MAX_ITER;
        this.ZOOM = ZOOM;
    }

    @Override
    public Map<Coordinates, Integer> call() {
        Map<Coordinates, Integer> result = new HashMap<>();
        double zx, zy, cX, cY, tmp;
        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                result.put(new Coordinates(x, y), iter);
            }
        }
        return result;
    }
}
