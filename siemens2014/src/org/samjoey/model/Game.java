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

    private ArrayList<Board> turns; //The list of boards
    private int winner; //0 for draws, 1 for white, 2 for black
    private String winType; //DRAW, FORFEIT, CHECKMATE, among others
    private double whiteTime; //How much time white took
    private double blackTime; //How much time black took
    private int plyCount; //The number of half-moves taken
    private int timeAllowedPerPlayer; //How much time was allowed per player
    private HashMap<String, ArrayList<Integer>> vars; //The variables calculated
    private Iterator iterator; //The iterator over the boards
    private int id;

    /**
     * Default constructor, initializes list and map
     */
    public Game() {
        turns = new ArrayList<>();
        vars = new HashMap<>();
    }

    /**
     * Initializes the iterator if it needs to and gets the next board.
     * @return the next board in the game.
     */
    public Board next() {
        if (iterator == null) {
            iterator = turns.iterator();
        }
        return (Board) iterator.next();
    }

    //Restart the iterator.
    public void restart() {
        iterator = turns.iterator();
    }

    //Return all variable data
    public HashMap<String, ArrayList<Integer>> getVarData() {
        return vars;
    }

    //Get a specific variable
    public ArrayList<Integer> getVar(String var) {
        return vars.get(var);
    }

    //Add data for a variable. Also handles adding the variable if it is not already mapped
    public void addVariable(String name, Integer value) {
        if (vars.containsKey(name)) {
            vars.get(name).add(value);
        } else {
            vars.put(name, new ArrayList<Integer>());
            vars.get(name).add(value);
        }
    }
    
    //Get all boards for this game
    public ArrayList<Board> getAllBoards(){
        return this.turns;
    }
    
    //Add a board to this game
    public void addBoard(Board b){
        this.turns.add(b);
    }

    //Get the winner of this game
    public int getWinner() {
        return winner;
    }

    //Set the winner of this game
    public void setWinner(int winner) {
        this.winner = winner;
    }

    //Get the way this game was won
    public String getWinType() {
        return winType;
    }

    //Set the way this game was won
    public void setWinType(String winType) {
        this.winType = winType;
    }

    //Get the amount time white took
    public double getWhiteTime() {
        return whiteTime;
    }

    //Set the amount of time white took
    public void setWhiteTime(double whiteTime) {
        this.whiteTime = whiteTime;
    }

    //Get the amount of time black took
    public double getBlackTime() {
        return blackTime;
    }

    //Set the amount of time black took
    public void setBlackTime(double blackTime) {
        this.blackTime = blackTime;
    }

    //Get how much time is allowed for each player
    public int getTimeAllowedPerPlayer() {
        return timeAllowedPerPlayer;
    }

    //Set how much time is allowed for each player
    public void setTimeAllowedPerPlayer(int timeAllowedPerPlayer) {
        this.timeAllowedPerPlayer = timeAllowedPerPlayer;
    }

    //Get the ply count
    public int getPlyCount() {
        return plyCount;
    }

    //Set the ply count
    public void setPlyCount(int plyCount) {
        this.plyCount = plyCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}