package com.company;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.Map;

public class Plotter extends ApplicationFrame {
    private XYSeriesCollection dataSeries;
    private String title;

    public Plotter(String title) {
        super(title);
        this.title = title;
        dataSeries = new XYSeriesCollection();
    }

    public void addSeries(String title, Map<Integer, Long> values) {
        dataSeries.addSeries(new XYSeries(title));
        for (Map.Entry<Integer, Long> e : values.entrySet()) {
            addValue(title, e.getKey(), e.getValue());
        }
    }

    private void addValue(String seriesTitle, int quantity, long time) {
        dataSeries.getSeries(seriesTitle).add(quantity, time);
    }

    public void plot() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "quantity", "waiting time", dataSeries
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane(chartPanel);
    }
}
