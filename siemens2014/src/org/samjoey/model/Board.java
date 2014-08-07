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

/**
 *
 * @author Sam
 */
public class Board {

    public static final String[][] INITIAL_BOARD = {{"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"},
                                             {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                             {"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"}};
    
    private String[][] pieces;
    private int whoseTurn; //0 if none, 1 if white, 2 if black
    private double time;
    private boolean opponentInCheck;
    
    public String[][] getAll(){
        return this.pieces;
    }
    
    public void setPositions(String[][] pieces){
        this.pieces = pieces;
    }
    
    public String get(int x, int y){
        return this.pieces[x - 1][y - 1];
    }
    
    public int getPlayer(){
        return this.whoseTurn;
    }
    
    public void setPlayer(int val){
        this.whoseTurn = val;
    }
    
    public double getTime(){
        return this.time;
    }
    
    public void setTime(double time){
        this.time = time;
    }

    public boolean isOpponentInCheck() {
        return opponentInCheck;
    }

    public void setOpponentInCheck(boolean opponentInCheck) {
        this.opponentInCheck = opponentInCheck;
    }
}
