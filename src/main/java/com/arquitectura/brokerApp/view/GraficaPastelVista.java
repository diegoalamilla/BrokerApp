/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.arquitectura.brokerApp.view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Miguel
 */
public class GraficaPastelVista {
    private JFrame frame;
    private ChartPanel chartPanel;
    private DefaultPieDataset dataset;

    public GraficaPastelVista() {
        frame = new JFrame("Gráfico de Pastel");
       
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        dataset = new DefaultPieDataset();
        JFreeChart pieChart = createPieChart(dataset);

        chartPanel = new ChartPanel(pieChart);
        frame.add(chartPanel);
        chartPanel.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public void updateChart(ArrayList<String> ProductNames, ArrayList<Integer> numberOfVotes) {
        dataset.clear();

        for (int i = 0; i < ProductNames.size(); i++) {
            dataset.setValue(ProductNames.get(i), numberOfVotes.get(i));
        }

        

    }
    public Frame getFrame() {
        return frame;
    }

    private JFreeChart createPieChart(DefaultPieDataset dataset) {
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Resultados de Votación", dataset, true, true, false);

        return pieChart;
    }

    public void closeWindow() {
        frame.dispose();
    }
}
