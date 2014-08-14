#!/usr/bin/env rhino

print("Hello, World!");

//importClass(Packages.org.model.Board);   // driver shouldn't interact with Boards
importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
importClass(Packages.org.samjoey.pattern.PatternFinder);
load("org/samjoey/gameLooper/GameLooper.js");  // import straight js
load("org/samjoey/calculator/calcDefs.js");


// in JS, use arguments vs args
var fileLoc = arguments[0];

// Used in Windows only start
/*if (arguments.indexOf("-s") > -1 || arguments.indexOf("--short-address") > -1) {
	var lastArg = arguments[arguments.length-1];
	var fileLoc = lastArg.substring(0, lastArg.length-4) + "docs/" + arguments[0];	// 4 for "src\"
}
fileLoc.replace("\\", "/", "g");
if (fileLoc.indexOf("C:") == 0) {
	fileLoc = fileLoc.substring(2);
}*/
// Done Windows only

fileLoc = 'File:' + fileLoc;


// Retrieve an ArrayList of Games to analyze
var gameList = Parser.parseGames(fileLoc);
// Get access to a GameLooper
var gameLooper = new GameLooper();
gameLooper.addCalculator(TotalisticUnweightedCenter('x'));
gameLooper.addCalculator(TotalisticWeightedCenter('x'));
gameLooper.addCalculator(TotalisticUnweightedCount);
gameLooper.addCalculator(TotalisticWeightedCount);
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
	print("-----------------------------------");
	gameLooper.read();
	
	// prepare to move on to the next
	gameLooper.close();
	
}
PatternFinder.findPatterns(gameList);