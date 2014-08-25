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
package org.samjoey.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartPanel;
import org.samjoey.graphing.GraphUtility;
import org.samjoey.model.Game;

/**
 *
 * @author Sam
 */
public class GraphicalViewer extends javax.swing.JFrame {

    private static final String WHITE_PAWN = "&#9817;";
    private static final String WHITE_BISHOP = "&#9815;";
    private static final String WHITE_KNIGHT = "&#9816;";
    private static final String WHITE_ROOK = "&#9814;";
    private static final String WHITE_QUEEN = "&#9813;";
    private static final String WHITE_KING = "&#9812;";
    private static final String BLACK_PAWN = "&#9823;";
    private static final String BLACK_BISHOP = "&#9821;";
    private static final String BLACK_KNIGHT = "&#9822;";
    private static final String BLACK_ROOK = "&#9820;";
    private static final String BLACK_QUEEN = "&#9819;";
    private static final String BLACK_KING = "&#9818;";
    private LinkedList<Game> games;
    private int currentGame;
    private int currentPly;
    private HashMap<String, ChartPanel> graphs;

    /**
     * Creates new form GraphicalViewer
     */
    public GraphicalViewer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Parser = new javax.swing.JPanel();
        jTextField_Parser = new javax.swing.JTextField();
        jButton_Parser_Open = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton_PreviousGame = new javax.swing.JButton();
        jButton_Next_Game = new javax.swing.JButton();
        jButton_Previous_Ply = new javax.swing.JButton();
        jButton_Next_Ply = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_Game_Viewer = new javax.swing.JTextPane();
        Ply_Label = new javax.swing.JLabel();
        Game_Label = new javax.swing.JLabel();
        Variable_Viewer_Panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Variable_Viewer_Textpane = new javax.swing.JTextPane();
        Graph_Panel = new javax.swing.JPanel();
        Variable_Chooser = new javax.swing.JComboBox();
        graphFrame = new javax.swing.JInternalFrame();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu_Open = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(20000, 20000));

        jPanel_Parser.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parser", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        jPanel_Parser.setName("Parser"); // NOI18N

        jTextField_Parser.setText("No File Chosen");
        jTextField_Parser.setEnabled(false);
        jTextField_Parser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ParserActionPerformed(evt);
            }
        });

        jButton_Parser_Open.setText("Parse");
        jButton_Parser_Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Parser_OpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_ParserLayout = new javax.swing.GroupLayout(jPanel_Parser);
        jPanel_Parser.setLayout(jPanel_ParserLayout);
        jPanel_ParserLayout.setHorizontalGroup(
            jPanel_ParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ParserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Parser_Open, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_Parser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel_ParserLayout.setVerticalGroup(
            jPanel_ParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ParserLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel_ParserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_Parser)
                    .addComponent(jButton_Parser_Open, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Game Viewer", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jButton_PreviousGame.setText("◄◄");
        jButton_PreviousGame.setToolTipText("Beginning of Game");
        jButton_PreviousGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PreviousGameActionPerformed(evt);
            }
        });

        jButton_Next_Game.setText("►►");
        jButton_Next_Game.setToolTipText("End of Game");
        jButton_Next_Game.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Next_GameActionPerformed(evt);
            }
        });

        jButton_Previous_Ply.setText("◄");
        jButton_Previous_Ply.setToolTipText("Previous Ply");
        jButton_Previous_Ply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Previous_PlyActionPerformed(evt);
            }
        });

        jButton_Next_Ply.setText("►");
        jButton_Next_Ply.setToolTipText("Next Ply");
        jButton_Next_Ply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Next_PlyActionPerformed(evt);
            }
        });

        jTextArea_Game_Viewer.setContentType("text/html"); // NOI18N
        jTextArea_Game_Viewer.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(jTextArea_Game_Viewer);

        Ply_Label.setText("Ply: ");

        Game_Label.setText("Game: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_PreviousGame)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_Previous_Ply, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Game_Label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_Next_Ply)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_Next_Game))
                            .addComponent(Ply_Label))
                        .addGap(11, 11, 11))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_PreviousGame)
                    .addComponent(jButton_Previous_Ply, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Next_Ply, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Next_Game))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Game_Label)
                    .addComponent(Ply_Label))
                .addGap(6, 6, 6))
        );

        Variable_Viewer_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Variable View", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        jScrollPane3.setMaximumSize(new java.awt.Dimension(75, 100));

        Variable_Viewer_Textpane.setEnabled(false);
        jScrollPane1.setViewportView(Variable_Viewer_Textpane);

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout Variable_Viewer_PanelLayout = new javax.swing.GroupLayout(Variable_Viewer_Panel);
        Variable_Viewer_Panel.setLayout(Variable_Viewer_PanelLayout);
        Variable_Viewer_PanelLayout.setHorizontalGroup(
            Variable_Viewer_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Variable_Viewer_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Variable_Viewer_PanelLayout.setVerticalGroup(
            Variable_Viewer_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Variable_Viewer_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        Graph_Panel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Graph Viewer", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        Variable_Chooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Variable..." }));
        Variable_Chooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Variable_ChooserActionPerformed(evt);
            }
        });

        graphFrame.setEnabled(false);
        graphFrame.setVisible(true);

        javax.swing.GroupLayout graphFrameLayout = new javax.swing.GroupLayout(graphFrame.getContentPane());
        graphFrame.getContentPane().setLayout(graphFrameLayout);
        graphFrameLayout.setHorizontalGroup(
            graphFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphFrameLayout.setVerticalGroup(
            graphFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Graph_PanelLayout = new javax.swing.GroupLayout(Graph_Panel);
        Graph_Panel.setLayout(Graph_PanelLayout);
        Graph_PanelLayout.setHorizontalGroup(
            Graph_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Graph_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Graph_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graphFrame)
                    .addComponent(Variable_Chooser, 0, 371, Short.MAX_VALUE))
                .addContainerGap())
        );
        Graph_PanelLayout.setVerticalGroup(
            Graph_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Graph_PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Variable_Chooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(graphFrame)
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenu_Open.setText("Open");
        jMenu_Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_OpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenu_Open);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Variable_Viewer_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_Parser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Graph_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Graph_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_Parser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Variable_Viewer_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu_OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_OpenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu_OpenActionPerformed

    private void jTextField_ParserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ParserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ParserActionPerformed

    private void jButton_Parser_OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Parser_OpenActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String[] exts = new String[1];
        exts[0] = "pgn";
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Only .pgn files", exts);
        fc.setFileFilter(filter);
        int returnVal = fc.showDialog(this, "Parse");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final java.io.File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            this.jTextField_Parser.setText(file.getName());
            (new Thread() {
                @Override
                public void run() {
                    selectedPGN(file);
                }
            }).start();
        } else {
        }
    }//GEN-LAST:event_jButton_Parser_OpenActionPerformed

    private void jButton_Next_GameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Next_GameActionPerformed
        if (currentGame < games.size() - 1) {
            this.setViewer(currentGame + 1, 0);
        }
    }//GEN-LAST:event_jButton_Next_GameActionPerformed

    private void jButton_Previous_PlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Previous_PlyActionPerformed
        if (currentPly > 0) {
            this.setViewer(currentGame, currentPly - 1);
        }
    }//GEN-LAST:event_jButton_Previous_PlyActionPerformed

    private void jButton_Next_PlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Next_PlyActionPerformed
        if (currentPly < games.get(currentGame).getPlyCount()) {
            this.setViewer(currentGame, currentPly + 1);
        }
    }//GEN-LAST:event_jButton_Next_PlyActionPerformed

    private void jButton_PreviousGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PreviousGameActionPerformed
        if (currentGame > 0) {
            this.setViewer(currentGame - 1, 0);
        }
    }//GEN-LAST:event_jButton_PreviousGameActionPerformed

    private void Variable_ChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Variable_ChooserActionPerformed
        String var = (String) this.Variable_Chooser.getSelectedItem();
        if (graphs.containsKey(var)) {
            graphFrame.setVisible(false);
            graphFrame.setContentPane(graphs.get(var));
            graphFrame.pack();
            graphFrame.setVisible(true);
        }
        System.out.println("Selected");
    }//GEN-LAST:event_Variable_ChooserActionPerformed

    private void selectedPGN(File file) {
        String fileLoc = file.getAbsolutePath();
        //fileLoc = "File:" + fileLoc.substring(2);
        for (int i = 0; i < fileLoc.length(); i++) {
            if (fileLoc.substring(i, i + 1).equals("/")) {
                fileLoc = fileLoc.substring(0, i) + "\\" + fileLoc.substring(i + 1);
            }
        }
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            // create JavaScript engine
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            // evaluate JavaScript code from given file - specified by first argument
            engine.put("engine", engine);
            ClassLoader cl = GraphicalViewer.class.getClassLoader();
            URL url = cl.getResource("\\org\\samjoey\\gameLooper\\GameLooper_1.js");
            String loopLoc = url.toString().substring(5);
            for (int i = 0; i < loopLoc.length() - 3; i++) {
                if (loopLoc.substring(i, i + 3).equals("%5c")) {
                    loopLoc = loopLoc.substring(0, i) + "/" + loopLoc.substring(i + 3);
                }
            }
            engine.put("loopLoc", loopLoc);
            url = cl.getResource("\\org\\samjoey\\calculator\\calcDefs_1.js");
            String defsLoc = url.toString().substring(5);
            for (int i = 0; i < defsLoc.length() - 3; i++) {
                if (defsLoc.substring(i, i + 3).equals("%5c")) {
                    defsLoc = defsLoc.substring(0, i) + "/" + defsLoc.substring(i + 3);
                }
            }
            engine.put("defsLoc", defsLoc);
            url = cl.getResource("\\org\\samjoey\\calculator\\Calculator.js");
            String calcLoc = url.toString().substring(5);
            for (int i = 0; i < calcLoc.length() - 3; i++) {
                if (calcLoc.substring(i, i + 3).equals("%5c")) {
                    calcLoc = calcLoc.substring(0, i) + "/" + calcLoc.substring(i + 3);
                }
            }
            engine.put("calcLoc", calcLoc);
            String args[] = {"-g:false", fileLoc};
            engine.put("arguments", args);
            engine.eval(new java.io.FileReader(GraphicalViewer.class.getClassLoader().getResource("driver_1.js").toString().substring(5)));
            games = (LinkedList<Game>) engine.get("gameList");
        } catch (ScriptException | FileNotFoundException ex) {
            Logger.getLogger(GraphicalViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Set<String> keys = games.get(0).getVarData().keySet();
        for (String key : keys) {
            this.Variable_Chooser.addItem(key);
        }
        graphs = GraphUtility.getGraphs(games);
        setViewer(0, 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphicalViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphicalViewer().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Game_Label;
    private javax.swing.JPanel Graph_Panel;
    private javax.swing.JLabel Ply_Label;
    private javax.swing.JComboBox Variable_Chooser;
    private javax.swing.JPanel Variable_Viewer_Panel;
    private javax.swing.JTextPane Variable_Viewer_Textpane;
    private javax.swing.JInternalFrame graphFrame;
    protected javax.swing.JButton jButton_Next_Game;
    protected javax.swing.JButton jButton_Next_Ply;
    private javax.swing.JButton jButton_Parser_Open;
    protected javax.swing.JButton jButton_PreviousGame;
    protected javax.swing.JButton jButton_Previous_Ply;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenu_Open;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Parser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextArea_Game_Viewer;
    private javax.swing.JTextField jTextField_Parser;
    // End of variables declaration//GEN-END:variables

    private void setViewer(int game, int ply) {
        this.Game_Label.setText("Game ID: " + games.get(game).getId());
        this.Ply_Label.setText("Ply: " + (ply + 1));
        this.currentGame = game;
        this.currentPly = ply;
        String viewer = "";
        String[][] board = games.get(game).getAllBoards().get(ply).getAll();
        for (String[] r : board) {
            for (String c : r) {
                viewer += "|";
                if (c.equals("")) {
                    viewer += "&#8199;&thinsp;&thinsp;&thinsp;";
                    continue;
                }
                if (c.equals("WP")) {
                    viewer += WHITE_PAWN;
                    continue;
                }
                if (c.equals("WB")) {
                    viewer += WHITE_BISHOP;
                    continue;
                }
                if (c.equals("WN")) {
                    viewer += WHITE_KNIGHT;
                    continue;
                }
                if (c.equals("WR")) {
                    viewer += WHITE_ROOK;
                    continue;
                }
                if (c.equals("WQ")) {
                    viewer += WHITE_QUEEN;
                    continue;
                }
                if (c.equals("WK")) {
                    viewer += WHITE_KING;
                    continue;
                }
                if (c.equals("BP")) {
                    viewer += BLACK_PAWN;
                    continue;
                }
                if (c.equals("BB")) {
                    viewer += BLACK_BISHOP;
                    continue;
                }
                if (c.equals("BN")) {
                    viewer += BLACK_KNIGHT;
                    continue;
                }
                if (c.equals("BR")) {
                    viewer += BLACK_ROOK;
                    continue;
                }
                if (c.equals("BQ")) {
                    viewer += BLACK_QUEEN;
                    continue;
                }
                if (c.equals("BK")) {
                    viewer += BLACK_KING;
                    continue;
                }
            }
            viewer += "<br>";
        }
        this.jTextArea_Game_Viewer.setText(viewer);

        String variables = "";
        HashMap<String, ArrayList<Double>> vars = games.get(game).getVarData();
        for (String key : vars.keySet()) {
            variables += key;
            variables += "= ";
            variables += ("" + vars.get(key).get(ply));
            variables += "\n";
        }
        this.Variable_Viewer_Textpane.setText(variables);
    }
}
