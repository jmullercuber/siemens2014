/*
 * Copyright 2014 Sam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.samjoey.graphing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class GraphUtility {

    /**
     *
     * @param games the games to graph
     * @param key the variable to create the graph with
     * @param start if index, the index at which to start, else the percent in
     * the game at which to start
     * @param stop if index, the index at which to end, else the percent in the
     * game at which to end
     * @param index
     * @return
     */
    public static ChartPanel getCustomGraph(Game[] games, String key, double start, double stop, boolean index) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Game game : games) {
            ArrayList<Double> data = game.getVar(key);
            int begin;
            int end;
            if (index) {
                begin = (int) start;
                end = (int) stop;
            } else {
                begin = (int) (data.size() / start);
                end = (int) (data.size() / stop);
            }
            XYSeries series = GraphUtility.createSeries(data.subList(begin, end), "" + game.getId());
            dataset.addSeries(series);
        }
        JFreeChart chart = ChartFactory.createXYLineChart(
                key, // chart title
                "X", // x axis label
                "Y", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
                );
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer rend = plot.getRenderer();
        for (int i = 0; i < games.length; i++) {
            Game g = games[i];
            if (g.getWinner() == 1) {
                rend.setSeriesPaint(i, Color.RED);
            }
            if (g.getWinner() == 2) {
                rend.setSeriesPaint(i, Color.BLACK);
            }
            if (g.getWinner() == 0) {
                rend.setSeriesPaint(i, Color.PINK);
            }
        }
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    public static HashMap<String, ChartPanel> getGraphs(LinkedList<Game> games) {
        HashMap<String, XYSeriesCollection> datasets = new HashMap<>();
        for (int j = 0; j < games.size(); j++) {
            Game game = games.get(j);
            if (game == null) {
                continue;
            }
            for (String key : game.getVarData().keySet()) {
                if (datasets.containsKey(key)) {
                    try {
                        datasets.get(key).addSeries(createSeries(game.getVar(key), "" + game.getId()));
                    } catch (Exception e) {
                    }
                } else {
                    datasets.put(key, new XYSeriesCollection());
                    datasets.get(key).addSeries(createSeries(game.getVar(key), "" + game.getId()));
                }
            }
        }
        HashMap<String, ChartPanel> chartPanels = new HashMap<>();
        for (String key : datasets.keySet()) {
            JFreeChart chart = ChartFactory.createXYLineChart(
                    key, // chart title
                    "X", // x axis label
                    "Y", // y axis label
                    datasets.get(key), // data
                    PlotOrientation.VERTICAL,
                    false, // include legend
                    true, // tooltips
                    false // urls
                    );
            XYPlot plot = chart.getXYPlot();
            XYItemRenderer rend = plot.getRenderer();
            for (int i = 0; i < games.size(); i++) {
                Game g = games.get(i);
                if (g.getWinner() == 1) {
                    rend.setSeriesPaint(i, Color.RED);
                }
                if (g.getWinner() == 2) {
                    rend.setSeriesPaint(i, Color.BLACK);
                }
                if (g.getWinner() == 0) {
                    rend.setSeriesPaint(i, Color.PINK);
                }
            }
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanels.put(key, chartPanel);
        }
        return chartPanels;
    }

    public static void createGraphs(LinkedList<Game> games) {
        HashMap<String, XYSeriesCollection> datasets = new HashMap<>();
        for (Game game : games) {
            for (String key : game.getVarData().keySet()) {
                if (datasets.containsKey(key)) {
                    try {
                        datasets.get(key).addSeries(createSeries(game.getVar(key), "" + game.getId()));
                    } catch (Exception e) {
                    }
                } else {
                    datasets.put(key, new XYSeriesCollection());
                    datasets.get(key).addSeries(createSeries(game.getVar(key), "" + game.getId()));
                }
            }
        }

        for (String key : datasets.keySet()) {
            JFrame f = new JFrame();
            JFreeChart chart = ChartFactory.createXYLineChart(
                    key, // chart title
                    "X", // x axis label
                    "Y", // y axis label
                    datasets.get(key), // data
                    PlotOrientation.VERTICAL,
                    false, // include legend
                    true, // tooltips
                    false // urls
                    );
            XYPlot plot = chart.getXYPlot();
            XYItemRenderer rend = plot.getRenderer();
            for (int i = 0; i < games.size(); i++) {
                Game g = games.get(i);
                if (g.getWinner() == 1) {
                    rend.setSeriesPaint(i, Color.RED);
                }
                if (g.getWinner() == 2) {
                    rend.setSeriesPaint(i, Color.BLACK);
                }
                if (g.getWinner() == 0) {
                    rend.setSeriesPaint(i, Color.PINK);
                }
            }
            ChartPanel chartPanel = new ChartPanel(chart);
            f.setContentPane(chartPanel);
            f.pack();
            RefineryUtilities.centerFrameOnScreen(f);
            f.setVisible(true);
        }
    }

    private static XYSeries createSeries(List<Double> var, String name) {
        XYSeries series = new XYSeries(name);
        int count = 0;
        for (Double d : var) {
            if (count > var.size()) {
                break;
            }
            series.add(count, d);
            count++;
        }
        return series;
    }
}
