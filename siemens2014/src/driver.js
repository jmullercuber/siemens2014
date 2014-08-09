#!/usr/bin/env rhino

print("Hello, World!");

//importClass(Packages.model.Board);
importClass(Packages.org.samjoey.model.Parser);
importClass(Packages.org.samjoey.model.Game);
// load("/org/samjoey/gameLooper/GameLooper.js")  // import straight js


// in JS, use arguments vs args
var fileLoc = arguments[0];


// Retrieve an ArrayList of Games to analyze
var gameList = Parser.parseGames(fileLoc);
// Get access to a GameLooper
var gameLooper = new GameLooper();
/*gameLooper.addCalculator(<calcNameHere>);
gameLooper.addCalculator(<calcNameHere>);
gameLooper.addCalculator(<calcNameHere>);
*/


// For every game...
for (var i=0; i<gameList.length(); i++) {
	// save to a variable
	var currentGame = gameList.get(i);
	
	// pass to the correct gameLooper
	// may have different gls finding different stuff
	gameLooper.open(currentGame);
	
	// do your magic!
	gameLooper.calculate();
	
	// prepare to move on to the next
	gameLooper.close();
	
}
