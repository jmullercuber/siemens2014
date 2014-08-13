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
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class Pattern {
    private static final double THRESHHOLD = .15;
    
    private ArrayList<Double> definition;
    private ArrayList<Game> matches;
    private ArrayList<Integer> positions;
    private String varType;
    
    public Pattern(ArrayList<Double> def, String var){
        this.definition = def;
        this.varType = var;
    }
    
    public void addGame(Game game, Integer position){
        matches.add(game);
        positions.add(position);
    }
    
    @Override
    public String toString(){
        String ret = "Pattern: " + super.toString() + "\n\r\t";
        ret += "Var: " + varType  + "\n\r\t";
        ret += "Games: [";
        for(Game g : matches){
            ret += g.getId() + ", ";
        }
        ret += "]\n\r\t";
        ret += "Positions: [";
        for(int i : positions){
            ret += i + ", ";
        }
        ret += "]\n\r\t";
        return ret;
    }
    
    public boolean doesGameMatch(Game g, int start){
        ArrayList<Double> check = g.getVar(varType);
        for(int i = 0; i < this.definition.size(); i++){
            int j = start + i;
            if(Math.abs(this.definition.get(i) - check.get(j)) > THRESHHOLD){
                return false;
            }
        }
        return true;
    }
}
