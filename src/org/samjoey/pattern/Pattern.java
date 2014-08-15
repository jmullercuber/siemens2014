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
public class Pattern {

    private static final double THRESHHOLD = .15;
    private ArrayList<Double> definition;
    private LinkedList<Game> matches;
    private ArrayList<Integer> positions;
    private String varType;

    public Pattern(ArrayList<Double> def, String var) {
        this.definition = def;
        this.varType = var;
        matches = new LinkedList<>();
        positions = new ArrayList<>();
    }

    public void addGame(Game game, Integer position) {
        matches.add(game);
        positions.add(position);
    }

    public LinkedList<Game> getMatches() {
        return matches;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }
    
    @Override
    public String toString() {
        String ret = "Pattern: " + super.toString() + "\n\r\t";
        ret += "Var: " + varType + "\n\r\t";
        ret += "Def: [";
        for (Double d : definition) {
            ret += d + ", ";
        }
        ret += "\b\b]\n\r\t";
        ret += "Games: [";
        for (Game g : matches) {
            ret += g.getId() + ", ";
        }
        ret += "\b\b]\n\r\t";
        ret += "Positions: [";
        for (int i : positions) {
            ret += i + ", ";
        }
        ret += "\b\b]\n\r\t";
        return ret;
    }

    public boolean doesGameMatch(Game g, int start) {
        ArrayList<Double> check = g.getVar(varType);
        for (int i = 0; i < this.definition.size() - 1; i++) {
            int j = start + i;
            if (Math.abs((this.definition.get(i) - this.definition.get(i + 1)) - (check.get(j) - check.get(j + 1))) > THRESHHOLD) {
                return false;
            }
        }
        return true;
    }
}
