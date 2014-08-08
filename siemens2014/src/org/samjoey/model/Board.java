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

    //The initial board layout. Just like a game of chess.
    public static final String[][] INITIAL_BOARD = {{"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"},
                                             {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"", "", "", "", "", "", "", ""},
                                             {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
                                             {"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"}};
    
    
    private String[][] pieces; //The piece layout for this board/turn
    private int whoseTurn; //0 if none, 1 if white, 2 if black
    private double time; //The time the player took to make this move
    private boolean opponentInCheck; //Did this move put the opponent in check?
    
    //Gets all moves
    public String[][] getAll(){
        return this.pieces;
    }
    
    //Sets all positions. Should be 8x8
    public void setPositions(String[][] pieces){
        this.pieces = pieces;
    }
    
    //Get piece at x,y if 1 >= x <= 8 && 1 >= y <= 8 
    public String get(int x, int y){
        return this.pieces[x - 1][y - 1];
    }
    
    //Get whose turn this is
    public int getPlayer(){
        return this.whoseTurn;
    }
    
    //Set the player
    public void setPlayer(int val){
        this.whoseTurn = val;
    }
    
    //Get the time it took to make this move
    public double getTime(){
        return this.time;
    }
    
    //Set the time it took to make this move
    public void setTime(double time){
        this.time = time;
    }

    //Check to see if this is a checking move
    public boolean isOpponentInCheck() {
        return opponentInCheck;
    }

    //Set if this is a checking move
    public void setOpponentInCheck(boolean opponentInCheck) {
        this.opponentInCheck = opponentInCheck;
    }
}