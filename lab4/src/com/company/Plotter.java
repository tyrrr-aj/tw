package com.company;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Plotter extends JPanel {
    private XYSeriesCollection dataSeries;
    private String title;

    public Plotter(String title) {
        super();
        this.title = title;
        dataSeries = new XYSeriesCollection();
    }

    public void addSeries(String title, Map<Float, Long> values) {
        dataSeries.addSeries(new XYSeries(title));
        for (Map.Entry<Float, Long> e : values.entrySet()) {
            addValue(title, e.getKey(), e.getValue());
        }
    }

    private void addValue(String seriesTitle, float quantity, long time) {
        dataSeries.getSeries(seriesTitle).add(quantity, time);
    }

    public void plot() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "quantity/bufferSize", "waiting time", dataSeries, PlotOrientation.VERTICAL, true, true, false
        );
//        Plot plot = chart.getPlot();
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
