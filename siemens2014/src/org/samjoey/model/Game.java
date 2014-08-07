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
package org.samjoey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Sam
 */
public class Game {

    private ArrayList<Board> turns;
    private int winner; //0 for draws, 1 for white, 2 for black
    private String winType; //DRAW, FORFEIT, CHECKMATE, among others
    private double whiteTime;
    private double blackTime;
    private int plyCount;
    private int timeAllowedPerPlayer;
    private HashMap<String, ArrayList<Integer>> vars;
    private Iterator iterator;

    public Game() {
        turns = new ArrayList<>();
        vars = new HashMap<>();
    }

    public Board next() {
        if (iterator == null) {
            iterator = turns.iterator();
        }
        return (Board) iterator.next();
    }

    public void restart() {
        iterator = turns.iterator();
    }

    public HashMap<String, ArrayList<Integer>> getVarData() {
        return vars;
    }

    public ArrayList<Integer> getVar(String var) {
        return vars.get(var);
    }

    public void addVariable(String name, Integer value) {
        if (vars.containsKey(name)) {
            vars.get(name).add(value);
        } else {
            vars.put(name, new ArrayList<Integer>());
            vars.get(name).add(value);
        }
    }
    
    public ArrayList<Board> getAllBoards(){
        return this.turns;
    }
    
    public void addBoard(Board b){
        this.turns.add(b);
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public String getWinType() {
        return winType;
    }

    public void setWinType(String winType) {
        this.winType = winType;
    }

    public double getWhiteTime() {
        return whiteTime;
    }

    public void setWhiteTime(double whiteTime) {
        this.whiteTime = whiteTime;
    }

    public double getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(double blackTime) {
        this.blackTime = blackTime;
    }

    public Iterator getIterator() {
        return iterator;
    }

    public void setIterator(Iterator iterator) {
        this.iterator = iterator;
    }

    public int getTimeAllowedPerPlayer() {
        return timeAllowedPerPlayer;
    }

    public void setTimeAllowedPerPlayer(int timeAllowedPerPlayer) {
        this.timeAllowedPerPlayer = timeAllowedPerPlayer;
    }

    public int getPlyCount() {
        return plyCount;
    }

    public void setPlyCount(int plyCount) {
        this.plyCount = plyCount;
    }
}
