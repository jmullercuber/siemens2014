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
import java.util.LinkedList;
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
                        if (p.doesGameMatch(game, i)) {
                            p.addGame(game, i);
                            added = true;
                        }
                    }
                    if(!added){
                        if(Math.abs(data.get(i) - data.get(i + 1)) < THRESHHOLD && Math.abs(data.get(i + 1) - data.get(i + 2)) < THRESHHOLD){
                            ArrayList<Double> def = new ArrayList<>();
                            def.add(data.get(i));
                            def.add(data.get(i + 1));
                            def.add(data.get(i + 2));
                            Pattern p = new Pattern(def, key);
                        }
                    }
                }
            }
        }
        if(pats.size() < 3){
            PatternFinder.THRESHHOLD -= .1;
            findPatterns(games);
            return;
        }
        for(Pattern p : pats){
            System.out.println(p);
        }
    }
}
