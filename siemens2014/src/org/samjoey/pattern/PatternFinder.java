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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class PatternFinder {

    private static HashMap<String, Double> threshholds;
    private static HashMap<String, Double> threshholds2;

    public static void findPatterns(LinkedList<Game> games) {
        HashMap<String, ArrayList<Pattern>> pats = new HashMap<>();
        pats.clear();
        if (threshholds == null) {
            threshholds = new HashMap<>();
        }
        if (threshholds2 == null) {
            threshholds2 = new HashMap<>();
        }
        for (Game game : games) {
            for (String key : game.getVarData().keySet()) {
                if (!threshholds.containsKey(key)) {
                    threshholds.put(key, .75);
                }
                if (threshholds.get(key) == -1) {
                    continue;
                }
                if (!pats.containsKey(key)) {
                    pats.put(key, new ArrayList<Pattern>());
                }
                double THRESHHOLD = threshholds.get(key);
                ArrayList<Double> data = game.getVarData().get(key);
                outer:
                for (int i = 0; i < data.size() - 5; i++) {
                    boolean added = false;
                    for (Pattern p : pats.get(key)) {
                        try {
                            if (p.doesGameMatch(game, i)) {
                                p.addGame(game, i);
                                added = true;
                                break outer;
                            }
                        } catch (Exception e) {
                            System.out.println("E" + 1);
                        }
                    }
                    if (!added) {
                        try {
                            if (Math.abs(data.get(i) - data.get(i + 1)) > THRESHHOLD && Math.abs(data.get(i + 1) - data.get(i + 2)) > THRESHHOLD && Math.abs(data.get(i + 2) - data.get(i + 3)) > THRESHHOLD && Math.abs(data.get(i + 3) - data.get(i + 4)) > THRESHHOLD) {
                                ArrayList<Double> def = new ArrayList<>();
                                def.add(data.get(i));
                                def.add(data.get(i + 1));
                                def.add(data.get(i + 2));
                                def.add(data.get(i + 3));
                                def.add(data.get(i + 4));
                                Pattern p = new Pattern(def, key);
                                pats.get(key).add(p);
                                break outer;
                            }
                        } catch (Exception e) {
                            System.out.println("E" + 2);
                        }
                    }
                }
            }
        }
        for (String key : pats.keySet()) {
            ArrayList<Pattern> ps = pats.get(key);
            for (int i = 0; i < ps.size(); i++) {
                if (ps.get(i).getMatches().size() < 5) {
                    ps.remove(i);
                    i--;
                }
            }
        }

        boolean repeat = false;
        for (String key : pats.keySet()) {
            ArrayList<Pattern> ps = pats.get(key);
            if (ps.size() < 5 && threshholds.get(key) > .0) {
                threshholds.put(key, threshholds.get(key) - .01);
                repeat = true;
            } else {
                threshholds2.put(key, new Double(threshholds.get(key)));
                //threshholds.put(key, -1.0);
            }
        }
        if (repeat) {
            //System.out.println(repeat);
            PatternFinder.findPatterns(games);
            return;
        }//        int i = (int) (Math.random() * pats.size());
        //        Pattern p = pats.get(i);
        //        System.out.println(p);
        //        createGraphs(p.getMatches());

        for (String key : pats.keySet()) {
            System.out.println(key + " : " + threshholds2.get(key));
            for (Pattern p : pats.get(key)) {
                System.out.println(p);
            }
        }

        //GraphUtility.createGraphs(games);
    }

    public static void findPatterns2(LinkedList<Game> games) {
    }
}
