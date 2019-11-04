package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class SeriesSummarizer {
    private Map<Integer, List<Long> > storedValues;
    private int bufferSize;

    public SeriesSummarizer(int bufferSize) {
        storedValues = new HashMap<>();
        this.bufferSize = bufferSize;
    }

    public synchronized void addValue(int X, long Y) {
        try {
            storedValues.get(X).add(Y);
        }
        catch (NullPointerException ignore) {
            List<Long> list = new ArrayList<>(1);
            list.add(Y);
            storedValues.put(X, list);
        }
    }

    public Map<Float, Long> getMeans() {
        return storedValues.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey() / (float) bufferSize, e -> average(e.getValue())));
    }

    private Long average(List<Long> values) {
        OptionalDouble average = values.stream()
                .mapToLong(a -> a)
                .average();
        return average.isPresent() ? Math.round(average.getAsDouble()) : 0;
    }
}
