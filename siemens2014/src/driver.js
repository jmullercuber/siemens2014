#!/usr/bin/env rhino

print("Hello, World!");

//importClass(Packages.org.model.Board);   // driver shouldn't interact with Boards
importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
importPackage(Packages.org.samjoey.calculator);
importClass(Packages.org.samjoey.pattern.PatternFinder);
importClass(Packages.org.samjoey.calculator.JCalculatorPawnMovement);
importClass(Packages.org.samjoey.calculator.JCalculatorWhiteChecks);
importClass(Packages.org.samjoey.calculator.JCalculatorBlackChecks);
importClass(Packages.org.samjoey.calculator.JCalculatorMoveTime);
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

//fileLoc = 'File:' + fileLoc;


// Retrieve an ArrayList of Games to analyze
var gameList = Parser.parseGames(fileLoc);
// Get access to a GameLooper
var gameLooper = new GameLooper();
gameLooper.addCalculator(TotalisticUnweightedCenter('x'));
gameLooper.addCalculator(TotalisticWeightedCenter('x'));
//gameLooper.addCalculator(PieceCountVars(white_or_black_or_all, weighted_or_unweighted));
gameLooper.addCalculator(PieceCountVars("White", "Weighted"));
gameLooper.addCalculator(PieceCountVars("White", "Unweighted"));
gameLooper.addCalculator(PieceCountVars("Black", "Weighted"));
gameLooper.addCalculator(PieceCountVars("Black", "Unweighted"));
gameLooper.addCalculator(PieceCountVars("Total", "Weighted"));
gameLooper.addCalculator(PieceCountVars("Total", "Unweighted"));
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
