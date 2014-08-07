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
package org.samjoey.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.samjoey.model.Board;
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class Parser {

    private static boolean print;

    public static void main(String[] args) {
        print = true;
        try {
            ArrayList<Game> games = parseGames("File:/Users/Sam/Documents/ficsgamesdb_201401_lightning2000_movetimes_1116420.pgn");
            //System.out.println(games.get(0).getWhiteTime());
        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println("ex");
        }
    }

    public static ArrayList<Game> parseGames(String fileLocation) throws URISyntaxException, IOException {
        File file = new File(new URI(fileLocation));
        return parseGames(file);
    }

    private static ArrayList<Game> parseGames(File file) throws IOException {
        BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("US-ASCII"));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        ArrayList<String[]> separatedGames = separateGameStrings(lines);
        return parseGameArrays(separatedGames);
    }

    private static ArrayList<String[]> separateGameStrings(ArrayList<String> lines) {
        String[] gameStrings = new String[20];
        ArrayList<String[]> separatedGameStrings = new ArrayList<>();
        int last = 0;
        for (String s : lines) {
            if (s.equals("") || s.substring(0, 1).equals("[")) {
                //System.out.println("1: " + s);
                gameStrings[last] = s;
                last++;
            } else {
                //System.out.println("2: " + s);
                gameStrings[last] = s;
                separatedGameStrings.add(gameStrings.clone());
                for (int i = 0; i < 20; i++) {
                    gameStrings[i] = "";
                }
                last = 0;
            }
        }

        return separatedGameStrings;
    }

    private static ArrayList<Game> parseGameArrays(ArrayList<String[]> separatedGames) {
        ArrayList<Game> games = new ArrayList<>();
        int count = 0;
        for (String[] strings : separatedGames) {
            Game game = new Game();
            count++;
            for (String s : strings) {

                if (s != null) {
                    if (s.contains("TimeControl")) {
                        game.setTimeAllowedPerPlayer(Integer.parseInt(s.substring(s.indexOf("\"") + 1, s.indexOf("+"))));
                    }
                    if (s.contains("PlyCount")) {
                        game.setPlyCount(Integer.parseInt(s.substring(s.indexOf("\"") + 1, s.indexOf("\"", s.indexOf("\"") + 1))));
                    }
                    if (s.contains("Result")) {
                        if (s.contains("1-0")) {
                            game.setWinner(1);
                        }
                        if (s.contains("0-1")) {
                            game.setWinner(2);
                        }
                        if (s.contains("1/2-1/2")) {
                            game.setWinner(0);
                        }
                    }
                    if (s.startsWith("1")) {
                        ArrayList<Board> boards = parseBoards(s, game);
                    }
                }
            }
            games.add(game);
        }
        return games;
    }

    private static ArrayList<Board> parseBoards(String s, Game game) {
        ArrayList<Double> times = new ArrayList<>();
        while (true) {
            int i = s.indexOf(" {[");
            if (i == -1) {
                break;
            }
            int j = s.indexOf("}") + 1;
            String time = s.substring(i, j);
            s = s.substring(0, i) + s.substring(j);
            Double t = Double.parseDouble(time.substring(time.indexOf(" ", 1) + 1, time.indexOf("]")));
            times.add(t);
        }
        Double whiteTime = new Double(0);
        Double blackTime = new Double(0);
        for (int i = 0; i < game.getPlyCount(); i++) {
            Double time = times.get(i);
            if (i % 2 == 0) {
                whiteTime = whiteTime + time;
            } else {
                blackTime = blackTime + time;
            }
        }
        game.setBlackTime(blackTime);
        game.setWhiteTime(whiteTime);
        ArrayList<String> split = new ArrayList<>(Arrays.asList(s.split(" ")));
        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < split.size(); i++) {
            if (split.get(i).contains("{")) {
                break;
            }
            if (!split.get(i).contains(".")) {
                moves.add(split.get(i));
            }
        }
        if (game.getPlyCount() != moves.size()) {
            System.out.println("MISMATCH: " + game.getPlyCount() + "," + moves.size() + ":" + moves.get(moves.size() - 1) + ":" + s);

        }
        ArrayList<Board> boards = new ArrayList<>();
        Board last = new Board();
        last.setPositions(Board.INITIAL_BOARD);
        last.setPlayer(0);
        last.setTime(0);
        boards.add(last);
        for (int i = 0; i < moves.size(); i++) {
            String str = moves.get(i);
            String orig = str;
            Board now = new Board();
            if (last.getPlayer() == 0 || last.getPlayer() == 2) {
                now.setPlayer(1);
            } else {
                now.setPlayer(2);
            }
            now.setTime(times.get(i));
            String special = checkForSpecialMoves(str);
            String piece = whatPiece(str);
            String pawnProm = "";
            if (!piece.equals("PAWN")) {
                str = str.substring(1);
            }
            String[][] old = last.getAll();
            if (special.contains("1")) {
                if (now.getPlayer() == 1) {
                    old[0][6] = old[0][4];
                    old[0][4] = "";
                    old[0][5] = old[0][7];
                    old[0][7] = "";
                }
                if (now.getPlayer() == 2) {
                    old[7][6] = old[7][4];
                    old[7][4] = "";
                    old[7][5] = old[7][7];
                    old[7][7] = "";
                }
            } else if (special.contains("2")) {
                if (now.getPlayer() == 1) {
                    old[0][2] = old[0][4];
                    old[0][4] = "";
                    old[0][3] = old[0][7];
                    old[0][7] = "";
                }
                if (now.getPlayer() == 2) {
                    old[7][2] = old[7][4];
                    old[7][4] = "";
                    old[7][3] = old[7][7];
                    old[7][7] = "";
                }
            } else if (!special.contains("O-O") && !special.contains("O-O-O")) {
                if (special.contains("3") || special.contains("4")) {
                    now.setOpponentInCheck(true);
                    str = str.substring(0, str.length() - 1);
                }
                if (special.contains("5")) {
                    pawnProm = whatPiece(str.substring(0, str.length() - 1));
                    str = str.substring(0, str.length() - 2);
                }
                if (special.contains("7")) {
                    str = str.substring(0, str.indexOf("x")) + str.substring(str.indexOf("x") + 1);
                }
                int disAmFile = 0;
                int disAmRank = 0;
                if (str.length() == 3) {
                    String f = str.substring(0, 1);
                    if (f.equals("a") || f.equals("b") || f.equals("c") || f.equals("d") || f.equals("e") || f.equals("f") || f.equals("g") || f.equals("h")) {
                        disAmFile = fileToNum(f);
                    } else {
                        disAmRank = Integer.parseInt(f);
                    }
                    str = str.substring(1);
                }
                if (str.length() == 4) {
                    disAmFile = fileToNum(str.substring(0, 1));
                    disAmRank = Integer.parseInt(str.substring(1, 2));
                    str = str.substring(2);
                }
                int file = 0;
                int rank = 0;
                try {
                    file = fileToNum(str.substring(0, 1));
                    rank = Integer.parseInt(str.substring(1));
                } catch (Exception e) {
                    System.out.println(i);
                    System.out.println(str.equals("O-O-O"));
                    System.out.println(old[0][2]);
                    System.out.println(old[0][4]);
                    System.out.println(disAmFile + "," + disAmRank + "," + file + "," + rank + "," + str);
                    System.out.println(special);
                    System.out.println(orig);
                    System.exit(1);
                }
                disAmFile --;
                disAmRank --;
                file --;
                rank --;
                if(piece.equals("PAWN")){
                    if(special.contains("7")){ //Pawn has to move diagonally
                        //DisAm Issue
                    } else{
                        if(now.getPlayer() == 1){
                            if(old[rank + 1][file].equals("WP")){
                                old[rank + 1][file] = "";
                                old[rank][file] = "WP";
                            }
                            if(old[rank + 2][file].equals("WP")){
                                old[rank + 2][file] = "";
                                old[rank][file] = "WP";
                            }
                        }
                        if(now.getPlayer() == 2){
                            if(old[rank - 1][file].equals("WP")){
                                old[rank - 1][file] = "";
                                old[rank][file] = "WP";
                            }
                            if(old[rank - 2][file].equals("WP")){
                                old[rank - 2][file] = "";
                                old[rank][file] = "WP";
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /*
     * blank = none
     * 1 = king castling
     * 2 = queen castling
     * 3 = checking move
     * 4 = checkmate
     * 5 = pawn promotion
     * 
     * 7 = capture
     * 
     * //To-Do : change when castling results in check
     */
    private static String checkForSpecialMoves(String str) {
        String ret = "";
        if (str.equals("O-O") || str.equals("O-O+")) {
            ret += "1";
        }
        if (str.equals("O-O-O") || str.equals("O-O-O+")) {
            ret += "2";
        }
        if (str.contains("+")) {
            ret += "3";
        }
        if (str.contains("#")) {
            ret += "4";
        }
        if (str.contains("=")) {
            ret += "5";
        }
        if (str.contains("x")) {
            ret += "7";
        }
        return ret;
    }

    private static String whatPiece(String str) {
        String s = str.substring(0, 1);
        if (s.equals("K")) {
            return "KING";
        }
        if (s.equals("Q")) {
            return "QUEEN";
        }
        if (s.equals("R")) {
            return "ROOK";
        }
        if (s.equals("N")) {
            return "KNIGHT";
        }
        if (s.equals("B")) {
            return "BISHOP";
        }
        return "PAWN";
    }

    private static int fileToNum(String f) {
        switch (f) {
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            default:
                return 0;
        }
    }
}
