#!/usr/bin/env rhino

print("Hello, World!");

//importClass(Packages.org.model.Board);   // driver shouldn't interact with Boards
importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
load("org/samjoey/gameLooper/GameLooper.js");  // import straight js
load("org/samjoey/calculator/calcDefs.js");


// in JS, use arguments vs args
var fileLoc = 'File:' + arguments[0];


// Retrieve an ArrayList of Games to analyze
var gameList = Parser.parseGames(fileLoc);
// Get access to a GameLooper
var gameLooper = new GameLooper();
gameLooper.addCalculator(TotalisticXUnweightedCenter);
// gameLooper.addCalculator(<calcNameHere>);


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
	gameLooper.read();
	
	// prepare to move on to the next
	gameLooper.close();
	
}
