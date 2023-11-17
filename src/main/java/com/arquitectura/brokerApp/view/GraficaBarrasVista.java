/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.arquitectura.brokerApp.view;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Miguel
 */
public class GraficaBarrasVista {
    private JFrame frame;
    private ChartPanel chartPanel;
    private DefaultCategoryDataset dataset;

    public GraficaBarrasVista() {
        frame = new JFrame("Gráfico de Barras");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        dataset = new DefaultCategoryDataset();
        JFreeChart barChart = createBarChart(dataset);
        
        chartPanel = new ChartPanel(barChart);
        frame.add(chartPanel);
        chartPanel.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public void updateChart(ArrayList<String> ProductNames, ArrayList<Integer> numberOfVotes) {
        dataset.clear();
       
        for (int i = 0; i < ProductNames.size(); i++) {
            dataset.setValue(numberOfVotes.get(i), "Votos", ProductNames.get(i));
        }
    }

    public Frame getFrame() {
        return frame;
    }

    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Resultados de Votación", "Productos", "Votos", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        CategoryItemRenderer renderer = new StackedBarRenderer();
        renderer.setSeriesItemLabelGenerator(0, null);
        renderer.setSeriesItemLabelGenerator(1, null);
        renderer.setSeriesItemLabelGenerator(2, null);
        renderer.setSeriesItemLabelsVisible(0, false);
        renderer.setSeriesItemLabelsVisible(1, false);
        renderer.setSeriesItemLabelsVisible(2, false);
        plot.setRenderer(renderer);
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
    
        return barChart;
    }

    public void closeWindow() {
        frame.dispose();
    }
}
