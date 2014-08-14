#!/usr/bin/env rhino

print("Hello, Graphics!");

importClass(Packages.org.samjoey.samples.graphicalLook);
importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
load("org/samjoey/gameLooper/GameLooper.js");  // import straight js
load("org/samjoey/calculator/calcDefs.js");

var guiFunctionality = {
	currentGame: null,
	
	selectedPGN: function(file) {
		// Retrieve an ArrayList of Games to analyze
		var gameList = Parser.parseGames(file);
		
		// Show it in the list
		if (gameList.size() > 0) {
			this.jList_GamesList.setListData(gameList.toArray());
			currentGame = gameList.get(0);
		 S}
		this.jLabelStatusBody.setText("Done parsing");
	}
}
var app = new JavaAdapter(graphicalLook, guiFunctionality);
app.setVisible(true);


jTable_Calculator
// Get access to a GameLooper
var gameLooper = new GameLooper();
gameLooper.addCalculator(TotalisticUnweightedCenter('x'));

/*
// For every game...
for (var i=0; i<gameList.size(); i++) {
	// save to a variable
	var currentGame = gameList.get(i);
	
	// pass to the correct gameLooper
	// may have different gls finding different stuff
	gameLooper.open(currentGame);
	
	// do your magic!
	gameLooper.calculate();
	
	// show the results
	print("-----------------------------------");
	gameLooper.read();
	
	// prepare to move on to the next
	gameLooper.close();
	
}*/
