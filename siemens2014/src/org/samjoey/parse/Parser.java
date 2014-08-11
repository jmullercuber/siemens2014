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
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Scanner;
import org.samjoey.model.Board;
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class Parser {

    private static boolean print;
    static boolean print2;
    static boolean print3;
    static boolean print4;
    static int enpassantCount;
    private static int noRookCount;
    private static int n;
    private static int checkmates;
    private static int drawn;
    private static int white;
    private static int black;
    private static int half;
    static boolean test;

    /**
     * Method for testing purposes. This will have no use in the actual program.
     */
    public static void main(String[] args) {
        print = false;
        print2 = false;
        print3 = false;
        print4 = false;
        n = 1;
        enpassantCount = 0;
        n--;
        noRookCount = 0;
        try {
            LinkedList<Game> games;
            //JOEY: you will need to change this in order to test.
            String fileLocation = "File:/Users/Sam/Documents/ficsgamesdb_201401_chess2000_movetimes_1118415.pgn";
            System.out.println(System.nanoTime());
            games = parseGames(fileLocation);
            System.out.println(System.nanoTime());
            //Print out a random game
            if (print2) {
                int get = (int) (Math.random() * games.size());

                Game game = games.get(get);
                ArrayList<Board> boards = game.getAllBoards();

                for (Board board : boards) {
                    System.out.println("   |A  |B  |C  |D  |E  |F  |G  |H");
                    for (int p = 7; p >= 0; p--) {
                        String[] y = board.getAll()[p];
                        System.out.print((p + 1) + ": |");
                        for (String x : y) {
                            if (!"".equals(x)) {
                                System.out.print(x + " |");
                            } else {
                                System.out.print("   |");
                            }
                        }
                        System.out.println();
                        System.out.println("-------------------------------------");
                    }
                    System.out.println("//");
                }
                System.out.println(get);
                System.out.println(game.getWinner());
                System.out.println(game.getPlyCount());
            }
            if (print3) {
                System.out.println(enpassantCount);
                ArrayList<Game> good = new ArrayList<>();
                ArrayList<Game> bad = new ArrayList<>();
                while (true) {
                    int get = (int) (Math.random() * games.size());
                    Game game = games.get(get);
                    //if (game.getWinType() != null && (game.getWinType().equals("Checkmated") || game.getWinType().equals("Draw")) ) {
                    if (game.getWinType() != null && (game.getWinType().equals("Draw"))) {
                        Board board = game.getAllBoards().get(game.getAllBoards().size() - 1);
                        System.out.println("   |A  |B  |C  |D  |E  |F  |G  |H");
                        for (int p = 7; p >= 0; p--) {
                            String[] y = board.getAll()[p];
                            System.out.print((p + 1) + ": |");
                            for (String x : y) {
                                if (!"".equals(x)) {
                                    System.out.print(x + " |");
                                } else {
                                    System.out.print("   |");
                                }
                            }
                            System.out.println();
                            System.out.println("-------------------------------------");
                        }

                        System.out.println(get + 1);
                        game.setId(get);
                        Scanner s = new Scanner(System.in);
                        while (!s.hasNext()) {
                        }
                        String in = s.next();
                        if (in.contains("G")) {
                            good.add(game);
                        }
                        if (in.contains("B")) {
                            bad.add(game);
                        }
                        if (in.contains("-")) {
                            break;
                        }
                    }
                }
                System.out.println("GOOD: " + good.size());
                for (Game g : good) {
                    System.out.println(g.getId());
                }
                System.out.println("BAD: " + bad.size());
                for (Game g : bad) {
                    System.out.println(g.getId());
                }
            }
            if (print4) {
                Game game = games.get(n);
                int count = 0;
                for (Board board : game.getAllBoards()) {
                    count++;
                    System.out.println(count);
                    System.out.println("   |A  |B  |C  |D  |E  |F  |G  |H");
                    for (int p = 7; p >= 0; p--) {
                        String[] y = board.getAll()[p];
                        System.out.print((p + 1) + ": |");
                        for (String x : y) {
                            if (!"".equals(x)) {
                                System.out.print(x + " |");
                            } else {
                                System.out.print("   |");
                            }
                        }
                        System.out.println();
                        System.out.println("-------------------------------------");
                    }
                    System.out.println("//");
                }

            }

        } catch (URISyntaxException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println("ex");
        }
    }

    /**
     * A method to initiate the parser. Takes a file location and retrieves the
     * file to parse and parses it.
     *
     * @param fileLocation the location of the pgn file to parse.
     * @return a list of games parsed from the file at fileLocation.
     * @throws URISyntaxException the location format is not valid.
     * @throws IOException there was an issue reading the file.
     */
    public static LinkedList<Game> parseGames(String fileLocation) throws URISyntaxException, IOException {
        File file = new File(new URI(fileLocation));
        return parseGames(file);
    }

    /**
     * Internal method called by parseGames(String fileLocation) after a file
     * has been created.
     *
     * @param file the file to parse from.
     * @returna list of games parsed from the file file.
     * @throws IOException
     */
    private static LinkedList<Game> parseGames(File file) throws IOException {
        BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("US-ASCII"));
        LinkedList<String> lines = new LinkedList<>();
        String line = null;
        //Read the input into a linked list.
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        LinkedList<String[]> separatedGames = separateGameStrings(lines);
        return parseGameArrays(separatedGames);
    }

    /**
     * Parses the initial lines into 'sets' that describe a game. Each set can
     * be a maximum of 20 lines and is described by an array. This is an
     * internal method.
     *
     * @param lines the input to separate
     * @return
     */
    private static LinkedList<String[]> separateGameStrings(LinkedList<String> lines) {
        String[] gameStrings = new String[20];
        LinkedList<String[]> separatedGameStrings = new LinkedList<>();
        int last = 0;
        for (String s : lines) {
            if (s.equals("") || s.substring(0, 1).equals("[")) {
                //System.out.println("1: " + s);
                gameStrings[last] = s;
                last++;
            } else {
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

    /**
     * Parses arrays of strings into a game. Each array describes a game from
     * the pgn. This is an internal method.
     *
     * @param separatedGames from separateGameStrings(LinkedList<String> lines).
     * @return
     */
    private static LinkedList<Game> parseGameArrays(LinkedList<String[]> separatedGames) {
        System.out.println(separatedGames.size());
        LinkedList<Game> games = new LinkedList<>();
        int count = 0;
        for (String[] strings : separatedGames) {
            Game game = new Game();
            count++;
            for (String s : strings) {

                if (s != null) {
                    //Get how much time is allotted to each player
                    if (s.contains("TimeControl")) {
                        game.setTimeAllowedPerPlayer(Integer.parseInt(s.substring(s.indexOf("\"") + 1, s.indexOf("+"))));
                    }
                    //Get how many half moves were played in the game
                    if (s.contains("PlyCount")) {
                        game.setPlyCount(Integer.parseInt(s.substring(s.indexOf("\"") + 1, s.indexOf("\"", s.indexOf("\"") + 1))));
                    }
                    //Determine the result of the game
                    if (s.contains("Result")) {
                        if (s.contains("1-0")) {
                            game.setWinner(1);
                            white++;
                        }
                        if (s.contains("0-1")) {
                            game.setWinner(2);
                            black++;
                        }
                        if (s.contains("1/2-1/2")) {
                            game.setWinner(0);
                            half++;
                        }
                    }
                    if (s.contains("checkmated")) {
                        game.setWinType("Checkmated");
                        Parser.checkmates++;
                    }
                    if (s.contains("resigns")) {
                        game.setWinType("Resignation");
                    }
                    if (s.contains("drawn")) {
                        game.setWinType("Draw");
                        Parser.drawn++;
                    }
                    //Parse the actual moves
                    if (s.startsWith("1")) {
                        //Parse boards from the string of moves.
                        LinkedList<Board> boards = parseBoards(s, game);
                        //Add the boards individually to the game
                        for (Board b : boards) {
                            game.addBoard(b);
                        }
                    }
                }
            }
            //Add the game to the list of games
            games.add(game);
            //To limit the number of games
            if (count % 100 == 0) {
                System.out.println(count);
            }
            if (count == 15000) {
                break;
            }
        }
        return games;
    }

    /**
     * Parses string of moves into a list of boards. This is an internal method.
     *
     * @param s the string of moves.
     * @param game the game to parse into.
     * @return
     */
    private static LinkedList<Board> parseBoards(String s, Game game) {
        LinkedList<Double> times = new LinkedList<>();
        //Remove time markers
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
        //Variables to represent each player's timess
        Double whiteTime = new Double(0);
        Double blackTime = new Double(0);
        //Add up the times
        for (int i = 0; i < game.getPlyCount(); i++) {
            Double time = times.get(i);
            if (i % 2 == 0) {
                whiteTime = whiteTime + time;
            } else {
                blackTime = blackTime + time;
            }
        }
        //Set the times
        game.setBlackTime(blackTime);
        game.setWhiteTime(whiteTime);
        //Separate the string into separate moves
        LinkedList<String> split = new LinkedList<>(Arrays.asList(s.split(" ")));
        LinkedList<String> moves = new LinkedList<>();
        for (int i = 0; i < split.size(); i++) {
            if (split.get(i).contains("{")) { // Signifies end of moves in moves string
                break;
            }
            if (!split.get(i).contains(".")) {
                moves.add(split.get(i));
            }
        }
        //The number of plies should equal the number of moves. Report if this is not true. (Note: has not been reported after numerous tests.)
        if (game.getPlyCount() != moves.size()) {
            System.out.println("MISMATCH: " + game.getPlyCount() + "," + moves.size() + ":" + moves.get(moves.size() - 1) + ":" + s);
        }
        //Start creating boards
        LinkedList<Board> boards = new LinkedList<>();
        Board last = new Board();
        //The first board will always be the initial board
        last.setPositions(Board.INITIAL_BOARD.clone());
        //Player starts at 0 (no one played the first board, it was set up
        last.setPlayer(0);
        last.setTime(0);
        //Add it
        boards.add(last);
        //Parse through each move and create a board for it
        for (int i = 0; i < moves.size(); i++) {
            //Get the move
            String str = moves.get(i);
            //Copy it in case of edit
            String orig = new String(str);
            Board now = new Board();
            //Set the player
            if (last.getPlayer() == 0 || last.getPlayer() == 2) {
                now.setPlayer(1);
            } else {
                now.setPlayer(2);
            }

            //For debugging purposes, print information if print is true
            if (print) {
                System.out.println(orig);
                System.out.println(now.getPlayer());
            }
            //Set the time for the move
            now.setTime(times.get(i));
            //Check for special cases in the move
            String special = checkForSpecialMoves(str);
            //Check what piece this move moves.
            String piece = whatPiece(str);
            //For holding pawn promos
            String pawnProm = "";
            //Get the pawn promo
            if (special.contains("5")) {
                pawnProm = str.substring(str.indexOf("=") + 1, str.indexOf("=") + 2);
            }
            //Remove the piece indicator. Pawns don't have indicators
            if (!piece.equals("PAWN")) {
                str = str.substring(1);
            }
            //Copy the previous
            String[][] old = new String[8][8];
            for (int a = 0; a < 8; a++) {
                old[a] = last.getAll()[a].clone();
            }
            //King castle
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
            } else if (special.contains("2")) { //Queen castle
                if (now.getPlayer() == 1) {
                    old[0][2] = "WK";
                    old[0][4] = "";
                    old[0][3] = "WR";
                    old[0][0] = "";
                }
                if (now.getPlayer() == 2) {
                    old[7][2] = "BK";
                    old[7][4] = "";
                    old[7][3] = "BR";
                    old[7][0] = "";
                }
            } else {
                if (special.contains("3") || special.contains("4")) { //Set opponent as being in check
                    now.setOpponentInCheck(true);
                    str = str.substring(0, str.length() - 1);
                }
                if (special.contains("5")) { //Get pawn promo and remove it from the move text
                    pawnProm = str.substring(str.indexOf("=") + 1);
                    str = str.substring(0, str.length() - 2);
                }
                if (special.contains("7")) { //Remove the 'x' is there has been a capture
                    str = str.substring(0, str.indexOf("x")) + str.substring(str.indexOf("x") + 1);
                }
                //Get any notation for disambiguation
                int disAmFile = 0;
                int disAmRank = 0;
                if (str.length() == 3) {
                    String f = str.substring(0, 1);
                    if (fileToNum(f) != 0) {
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

                //Get the file and rank for the move
                int file = 0;
                int rank = 0;
                try {
                    file = fileToNum(str.substring(0, 1));
                    rank = Integer.parseInt(str.substring(1));
                } catch (Exception e) {
                }
                //Convert to computer numbers
                disAmFile--;
                disAmRank--;
                file--;
                rank--;
                //Move pawn if the move is for a pawn
                if (piece.equals("PAWN")) { //No dealing with en passant yet
                    if (special.contains("7")) { //Pawn has to move diagonally
                        //DisAm Issue
                        if (old[rank][file].equals("")) {
                            enpassantCount++;
                            if (now.getPlayer() == 1) {
                                old[rank - 1][file] = "";
                            } else {
                                old[rank + 1][file] = "";
                            }
                        }
                        if (now.getPlayer() == 1) {
                            String p1 = "";
                            String p2 = "";
                            try {
                                p1 = old[rank - 1][file - 1];
                            } catch (Exception e) {
                            }
                            try {
                                p2 = old[rank - 1][file + 1];
                            } catch (Exception e) {
                            }
                            String c = old[rank][file]; //For dealing with en passant issue
                            if (p1.equals("WP") && p2.equals("WP")) {
                                old[rank][file] = "WP";
                                old[rank - 1][disAmFile] = "";
                            } else if (p1.equals("WP")) {
                                old[rank][file] = "WP";
                                old[rank - 1][file - 1] = "";
                            } else if (p2.equals("WP")) {
                                old[rank][file] = "WP";
                                old[rank - 1][file + 1] = "";
                            } else {
                                if (print) {
                                    System.out.println("ERROR: NO PAWN FOUND, P1");
                                }
                            }
                        } else {
                            String p1 = "";
                            String p2 = "";
                            try {
                                p1 = old[rank + 1][file - 1];
                            } catch (Exception e) {
                            }
                            try {
                                p2 = old[rank + 1][file + 1];
                            } catch (Exception e) {
                            }
                            String c = old[rank][file]; //For dealing with en passant issue
                            if (p1.equals("BP") && p2.equals("BP")) {
                                old[rank][file] = "BP";
                                old[rank + 1][disAmFile] = "";
                            } else if (p1.equals("BP")) {
                                old[rank][file] = "BP";
                                old[rank + 1][file - 1] = "";
                            } else if (p2.equals("BP")) {
                                old[rank][file] = "BP";
                                old[rank + 1][file + 1] = "";
                            } else {
                                if (print) {
                                    System.out.println("ERROR: NO PAWN FOUND, P2");
                                }
                            }
                        }
                    } else {
                        if (now.getPlayer() == 1) {
                            try {
                                if (old[rank - 1][file].equals("WP")) {
                                    old[rank - 1][file] = "";
                                    old[rank][file] = "WP";
                                } else if (old[rank - 2][file].equals("WP")) {
                                    old[rank - 2][file] = "";
                                    old[rank][file] = "WP";
                                }
                            } catch (Exception e) {
                            }
                        }
                        if (now.getPlayer() == 2) {
                            try {
                                if (old[rank + 1][file].equals("BP")) {
                                    old[rank + 1][file] = "";
                                    old[rank][file] = "BP";
                                } else if (old[rank + 2][file].equals("BP")) {
                                    old[rank + 2][file] = "";
                                    old[rank][file] = "BP";
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    if (!pawnProm.equals("")) {
                        String c = "B";
                        if (now.getPlayer() == 1) {
                            c = "W";
                        }
                        old[rank][file] = c + pawnProm;
                    }
                }
                //Move bishops
                if (piece.equals("BISHOP")) {
                    if (now.getPlayer() == 1) {
                        ArrayList<int[]> bs = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("WB")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    bs.add(x);
                                }
                            }
                        }
                        for (int j = 0; j < bs.size(); j++) {
                            int[] x = bs.get(j);
                            if (Math.abs(rank - x[0]) != Math.abs(file - x[1])) {
                                bs.remove(x);
                            }
                        }
                        for (int j = 0; j < bs.size(); j++) {
                            int[] x = bs.get(j);
                            for (int r = 0; r < 8; r++) {
                                if ((r > rank && r < x[0]) || (r < rank && r > x[0])) {
                                    for (int f = 0; f < 8; f++) {
                                        if ((f > file && f < x[1]) || (f < file && f > x[1])) {
                                            if (Math.abs(rank - r) == Math.abs(file - f)) {
                                                if (!old[r][f].equals("")) {
                                                    try {
                                                        bs.remove(j);
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (bs.size() == 1) {
                            old[bs.get(0)[0]][bs.get(0)[1]] = "";
                            old[rank][file] = "WB";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : bs) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WB";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : bs) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WB";
                                    }
                                }
                            }

                        }
                    } else {
                        ArrayList<int[]> bs = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("BB")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    bs.add(x);
                                }
                            }
                        }
                        for (int j = 0; j < bs.size(); j++) {
                            int[] x = bs.get(j);
                            if (Math.abs(rank - x[0]) != Math.abs(file - x[1])) {
                                bs.remove(x);
                            }
                        }
                        for (int j = 0; j < bs.size(); j++) {
                            int[] x = bs.get(j);
                            for (int r = 0; r < 8; r++) {
                                if ((r > rank && r < x[0]) || (r < rank && r > x[0])) {
                                    for (int f = 0; f < 8; f++) {
                                        if ((f > file && f < x[1]) || (f < file && f > x[1])) {
                                            if (Math.abs(rank - r) == Math.abs(file - f)) {
                                                if (!old[r][f].equals("")) {
                                                    try {
                                                        bs.remove(j);
                                                    } catch (Exception e) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (bs.size() == 1) {
                            old[bs.get(0)[0]][bs.get(0)[1]] = "";
                            old[rank][file] = "BB";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : bs) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BB";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : bs) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BB";
                                    }
                                }
                            }

                        }
                    }
                }


                if (piece.equals("KNIGHT")) {
                    if (now.getPlayer() == 1) {
                        ArrayList<int[]> ks = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("WN")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    ks.add(x);
                                }
                            }
                        }
                        for (int j = 0; j < ks.size(); j++) {
                            int[] x = ks.get(j);
                            if (!((Math.abs(rank - x[0]) == 1 && Math.abs(file - x[1]) == 2) || (Math.abs(rank - x[0]) == 2 && Math.abs(file - x[1]) == 1))) {
                                ks.remove(x);
                            }
                        }
                        if (ks.size() == 1) {
                            old[ks.get(0)[0]][ks.get(0)[1]] = "";
                            old[rank][file] = "WN";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : ks) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WN";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : ks) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WN";
                                    }
                                }
                            }

                        }
                    } else {
                        ArrayList<int[]> ks = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("BN")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    ks.add(x);
                                }
                            }
                        }
                        for (int j = 0; j < ks.size(); j++) {
                            int[] x = ks.get(j);
                            if (!((Math.abs(rank - x[0]) == 1 && Math.abs(file - x[1]) == 2) || (Math.abs(rank - x[0]) == 2 && Math.abs(file - x[1]) == 1))) {
                                ks.remove(x);
                            }
                        }
                        if (ks.size() == 1) {
                            old[ks.get(0)[0]][ks.get(0)[1]] = "";
                            old[rank][file] = "BN";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : ks) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BN";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : ks) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BN";
                                    }
                                }
                            }

                        }
                    }
                }
                if (piece.equals("ROOK")) { //Moves rooks
                    if (now.getPlayer() == 1) {
                        ArrayList<int[]> rs = new ArrayList<>(); //For storing the coordinates of all rooks on the board
                        for (int r = 0; r < 8; r++) { //Parse through every rank
                            for (int f = 0; f < 8; f++) { //Parse through every file
                                if (old[r][f].equals("WR")) { //If the piece there is a white rook, store its coordinates
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    rs.add(x);
                                }
                            }
                        } //End parsing through entire board
                        for (int j = 0; j < rs.size(); j++) { //Parse through each potential rook to make sure it would move straight
                            int[] x = rs.get(j); //Get the rook
                            if (!(rank - x[0] == 0 || file - x[1] == 0)) { // Ranks should be equal or files should be equal
                                rs.remove(x); //If not, remove it
                            }
                        }
                        if (rs.isEmpty()) {
                            noRookCount++;
                        } else if (rs.size() == 1) { //If there is only one, move it
                            old[rs.get(0)[0]][rs.get(0)[1]] = "";
                            old[rank][file] = "WR";
                        } else { //There can only be one
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : rs) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WR";
                                    }
                                }
                            }
                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : rs) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WR";
                                    }
                                }
                            }

                            //
                            if (disAmFile != -1 && disAmRank != -1) {
                                for (int[] x : rs) {
                                    if (x[0] == disAmRank && x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WR";
                                    }
                                }
                            }
                            if (disAmFile == -1 && disAmRank == -1) { //If no disAm
                                int[] a = rs.get(0); //Get #1
                                int[] b = rs.get(1); //Get #2
                                if (a[0] == b[0]) { //If ranks are equal
                                    if (file > b[1]) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "WR";
                                    } else if (file < a[1]) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "WR";
                                    } else {
                                        boolean x = true;
                                        for (int f = b[1] - 1; f > file; f--) {
                                            if (!old[b[0]][f].equals("")) {
                                                x = false;
                                                break;
                                            }
                                        }
                                        if (x) {
                                            old[b[0]][b[1]] = "";
                                            old[rank][file] = "WR";
                                        } else {
                                            old[a[0]][a[1]] = "";
                                            old[rank][file] = "WR";
                                        }
                                    }
                                } else {
                                    if (file > b[0]) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "WR";
                                    } else if (file < a[0]) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "WR";
                                    } else {
                                        boolean x = true;
                                        for (int f = b[0] - 1; f > file; f--) {
                                            if (!old[f][b[1]].equals("")) {
                                                x = false;
                                                break;
                                            }
                                        }
                                        if (x) {
                                            old[b[0]][b[1]] = "";
                                            old[rank][file] = "WR";
                                        } else {
                                            old[a[0]][a[1]] = "";
                                            old[rank][file] = "WR";
                                        }

                                    }
                                }
                            }
                        }
                    } else {
                        ArrayList<int[]> rs = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("BR")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    rs.add(x);
                                }
                            }
                        }
                        for (int j = 0; j < rs.size(); j++) {
                            int[] x = rs.get(j);
                            if (!(rank - x[0] == 0 || file - x[1] == 0)) {
                                rs.remove(x);
                            }
                        }
                        if (rs.isEmpty()) {
                            noRookCount++;
                        } else if (rs.size() == 1) {
                            old[rs.get(0)[0]][rs.get(0)[1]] = "";
                            old[rank][file] = "BR";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : rs) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BR";
                                    }
                                }
                            }
                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : rs) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BR";
                                    }
                                }
                            }
                            //
                            if (disAmFile != -1 && disAmRank != -1) {
                                for (int[] x : rs) {
                                    if (x[0] == disAmRank && x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BR";
                                    }
                                }
                            }
                            if (disAmFile == -1 && disAmRank == -1) {
                                int[] a = rs.get(0);
                                int[] b = rs.get(1);
                                if (a[0] == b[0]) {
                                    if (file > b[1]) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "BR";
                                    } else if (file < a[1]) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "BR";
                                    } else {
                                        boolean x = true;
                                        for (int f = b[1] - 1; f > file; f--) {
                                            if (!old[b[0]][f].equals("")) {
                                                x = false;
                                                break;
                                            }
                                        }
                                        if (x) {
                                            old[b[0]][b[1]] = "";
                                            old[rank][file] = "BR";
                                        } else {
                                            old[a[0]][a[1]] = "";
                                            old[rank][file] = "BR";
                                        }
                                    }
                                } else {
                                    if (file > b[0]) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "BR";
                                    } else if (file < a[0]) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "BR";
                                    } else {
                                        boolean x = true;
                                        for (int f = b[0] - 1; f > file; f--) {
                                            if (!old[f][b[1]].equals("")) {
                                                x = false;
                                                break;
                                            }
                                        }
                                        if (x) {
                                            old[b[0]][b[1]] = "";
                                            old[rank][file] = "BR";
                                        } else {
                                            old[a[0]][a[1]] = "";
                                            old[rank][file] = "BR";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (piece.equals("QUEEN")) {
                    if (now.getPlayer() == 1) {
                        ArrayList<int[]> ks = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("WQ")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    ks.add(x);
                                }
                            }
                        }
                        if (ks.isEmpty()) {
                        } else if (ks.size() == 1) {
                            old[ks.get(0)[0]][ks.get(0)[1]] = "";
                            old[rank][file] = "WQ";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : ks) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WQ";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : ks) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "WQ";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank == -1) {
                                int[] a = ks.get(0);
                                int[] b = ks.get(1);
                                int f = a[1];
                                int r = a[0];
                                while (true) {
                                    if (f != file) {
                                        if (f < file) {
                                            f++;
                                        } else {
                                            f--;
                                        }
                                    }
                                    if (r != rank) {
                                        if (r < rank) {
                                            r++;
                                        } else {
                                            r--;
                                        }
                                    }
                                    if (r == rank && f == file) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "WQ";
                                        break;
                                    }
                                    if (!old[r][f].equals("")) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "WQ";
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        ArrayList<int[]> ks = new ArrayList<>();
                        for (int r = 0; r < 8; r++) {
                            for (int f = 0; f < 8; f++) {
                                if (old[r][f].equals("BQ")) {
                                    int[] x = new int[2];
                                    x[0] = r;
                                    x[1] = f;
                                    ks.add(x);
                                }
                            }
                        }
                        if (ks.isEmpty()) {
                        } else if (ks.size() == 1) {
                            old[ks.get(0)[0]][ks.get(0)[1]] = "";
                            old[rank][file] = "BQ";
                        } else {
                            if (disAmFile != -1 && disAmRank == -1) {
                                for (int[] x : ks) {
                                    if (x[1] == disAmFile) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BQ";
                                    }
                                }
                            }

                            if (disAmFile == -1 && disAmRank != -1) {
                                for (int[] x : ks) {
                                    if (x[0] == disAmRank) {
                                        old[x[0]][x[1]] = "";
                                        old[rank][file] = "BQ";
                                    }
                                }
                            }
                            if (disAmFile == -1 && disAmRank == -1) {
                                int[] a = ks.get(0);
                                int[] b = ks.get(1);
                                int f = a[1];
                                int r = a[0];
                                while (true) {
                                    if (f != file) {
                                        if (f < file) {
                                            f++;
                                        } else {
                                            f--;
                                        }
                                    }
                                    if (r != rank) {
                                        if (r < rank) {
                                            r++;
                                        } else {
                                            r--;
                                        }
                                    }
                                    if (r == rank && f == file) {
                                        old[a[0]][a[1]] = "";
                                        old[rank][file] = "BQ";
                                        break;
                                    }
                                    if (!old[r][f].equals("")) {
                                        old[b[0]][b[1]] = "";
                                        old[rank][file] = "BQ";
                                        break;
                                    }
                                }
                            }
                        }
                    }

                }
                if (piece.equals("KING")) {
                    if (now.getPlayer() == 1) {
                        int f = 0;
                        int r = 0;
                        first:
                        for (int rr = 0; rr < 8; rr++) {
                            for (int ff = 0; ff < 8; ff++) {
                                if (old[rr][ff].equals("WK")) {
                                    f = ff;
                                    r = rr;
                                    break first;
                                }
                            }
                        }
                        old[rank][file] = "WK";
                        old[r][f] = "  ";
                    } else {
                        int f = 0;
                        int r = 0;
                        first:
                        for (int rr = 0; rr < 8; rr++) {
                            for (int ff = 0; ff < 8; ff++) {
                                if (old[rr][ff].equals("BK")) {
                                    f = ff;
                                    r = rr;
                                    break first;
                                }
                            }
                        }
                        old[rank][file] = "BK";
                        old[r][f] = "  ";
                    }
                }
            }
            now.setPositions(old.clone());
            boards.add(now);
            last = new Board();
            last.setPlayer(now.getPlayer());
            last.setPositions(now.getAll().clone());
            last.setTime(now.getTime());
            last.setOpponentInCheck(now.isOpponentInCheck());
            if (print) {
                System.out.println("   |A  |B  |C  |D  |E  |F  |G  |H");
                for (int p = 7; p >= 0; p--) {
                    String[] y = now.getAll()[p];
                    System.out.print((p + 1) + ": |");
                    for (String x : y) {
                        if (!"".equals(x)) {
                            System.out.print(x + " |");
                        } else {
                            System.out.print("   |");
                        }
                    }
                    System.out.println();
                    System.out.println("-------------------------------------");
                }
                System.out.println("//");
            }
        }
        if (print) {
            System.out.println();
            System.out.println();
        }
        print = false;
        return boards;
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

    //Gets the piece type
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
