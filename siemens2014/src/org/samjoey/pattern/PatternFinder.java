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
package org.samjoey.pattern;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
public class PatternFinder {

    private static double THRESHHOLD = .75;

    public static void findPatterns(LinkedList<Game> games) {
        ArrayList<Pattern> pats = new ArrayList<>();
        for (Game game : games) {
            for (String key : game.getVarData().keySet()) {
                ArrayList<Double> data = game.getVarData().get(key);
                for (int i = 0; i < data.size() - 3; i++) {
                    boolean added = false;
                    for (Pattern p : pats) {
                        try {
                            if (p.doesGameMatch(game, i)) {
                                p.addGame(game, i);
                                added = true;
                            }
                        } catch (Exception e) {
                            System.out.println("E" + 1);
                        }
                    }
                    if (!added) {
                        try {
                            if (Math.abs(data.get(i) - data.get(i + 1)) > THRESHHOLD && Math.abs(data.get(i + 1) - data.get(i + 2)) > THRESHHOLD) {
                                ArrayList<Double> def = new ArrayList<>();
                                def.add(data.get(i));
                                def.add(data.get(i + 1));
                                def.add(data.get(i + 2));
                                Pattern p = new Pattern(def, key);
                                pats.add(p);
                            }
                        } catch (Exception e) {
                            System.out.println("E" + 2);
                        }
                    }
                }
            }
        }
        if (pats.size() < 3 && THRESHHOLD > 0) {
            PatternFinder.THRESHHOLD -= .1;
            findPatterns(games);
            return;
        }

//        int i = (int) (Math.random() * pats.size());
//        Pattern p = pats.get(i);
//        System.out.println(p);
//        createGraphs(p.getMatches());
//        for (Pattern p : pats) {
//            System.out.println(p);
//        }
//        System.out.println(THRESHHOLD);
        createGraphs(games);
    }

    public static void createGraphs(LinkedList<Game> games) {
        HashMap<String, XYSeriesCollection> datasets = new HashMap<>();
        for (Game game : games) {
            for (String key : game.getVarData().keySet()) {
                if (datasets.containsKey(key)) {
                    try {
                        datasets.get(key).addSeries(createSeries(game.getVar(key), game.getId()));
                    } catch (Exception e) {
                    }
                } else {
                    datasets.put(key, new XYSeriesCollection());
                    datasets.get(key).addSeries(createSeries(game.getVar(key), game.getId()));
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
            for(int i = 0; i < games.size(); i ++){
                Game g = games.get(i);
                if(g.getWinner() == 1){
                    rend.setSeriesPaint(i, Color.RED);
                } 
                if(g.getWinner() == 2){
                    rend.setSeriesPaint(i, Color.BLACK);
                } 
                if(g.getWinner() == 0){
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

    private static XYSeries createSeries(ArrayList<Double> var, int id) {
        XYSeries series = new XYSeries(id);
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
