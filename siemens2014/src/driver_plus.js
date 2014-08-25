//*************************Calculator.js*****************************


function Calculator(nm, func, rst) {
	this.name = nm;
	this.evaluate = func;
	this.reset = rst || function(){};
}

//*************************calcDefs.js*****************************


/*
 * Here place definitions for various pre-made Calculators
 * These names will be imported to the driver, and will
 * have local meaning there.
 * Purpose: To not culter up that precious driver file 
 */


// CenterOfMass is a function factory
var CenterOfMass = function(x_or_y, pieceWeight, filterFunc, calcName) {
	// arguments
	//  In what direction shouid we calculate? 'x', or file, by default
	x_or_y = x_or_y || 'x';
	
	// How much is each piece worth? Weight each piece's value
	pieceWeight = pieceWeight || (function(pieceID) {return 1;});
	
	// What types of pieces do we want? Filter the list
	filterFunc = filterFunc || (function(pieceEntry) {return true;});
	
	// And give it a name!
	calcName = calcName || "CenterOfMass";
	
	return new Calculator(
		calcName,
		function(board) {
			// arguments
			// Get a list of pieces...
			var pList = board.exportPiecesToList();
			// ... and filter it
			var pieceList = [];
			for (var i=0; i<pList.size(); i++) {
				if (filterFunc(pList.get(i))) {
					pieceList.push(pList.get(i));
				}
			}
			
			// partial sum variables
			var sum_mass_pos = {
				'x':0,
				'y':0
			};
			var total_mass = 0;
			
			// Figure Center of Mass by summing weights for every piece
			for (var i=0; i<pieceList.length; i++) {
				var pieceData = pieceList[i];
				
				sum_mass_pos['x'] += pieceWeight(pieceData.get(0))*pieceData.get(1);
				sum_mass_pos['y'] += pieceWeight(pieceData.get(0))*pieceData.get(2);
				total_mass += pieceWeight(pieceData.get(0));
			}
			
			center_of_mass = sum_mass_pos[x_or_y]/total_mass;
			return center_of_mass;
		}
	);
 };


// PieceCount is a function factory
var PieceCount = function(pieceWeight, filterFunc, calcName) {
	// arguments
	
	// How much is each piece worth? Weight each piece's value
	pieceWeight = pieceWeight || (function(pieceID) {return 1;});
	
	// What types of pieces do we want? White, Black, All? Filter the list
	filterFunc = filterFunc || (function(pieceEntry, board) {return true;});
	
	// And give it a name!
	calcName = calcName || "PieceCount";
	
	return new Calculator(
		calcName,
		function(board) {
			// arguments
			// Get a list of pieces...
			var pList = board.exportPiecesToList();
			// ... and filter it
			var pieceList = [];
			for (var i=0; i<pList.size(); i++) {
				if (filterFunc(pList.get(i), board)) {
					pieceList.push(pList.get(i));
				}
			}
			
			// partial sum
			var total_mass = 0;
			
			// Figure Count by summing weights for every piece
			for (var i=0; i<pieceList.length; i++) {
				var pieceData = pieceList[i];
				
				total_mass += pieceWeight(pieceData.get(0));
			}
			
			return total_mass;
		}
	);
 };


// Also a function-factory, but lazily, and relies on PieceCount
var AveragePieceValue = function(white_or_black_or_all) {
	// AvgValue cheats off weighted piece count
	// All the eval really needs to do is take the sum, and divide by amount
	var weightedCount = new PieceCountVars(white_or_black_or_all, "Weighted");
	var copyEvalFunc = weightedCount.evaluate;	    // NOTE there are  NOO parenteses!! We want the function, not the value
	
	// Figure how many pieces, black, white, or all
	//var copyFilterFunc = weightedCount.;
	var filterFunction;
	switch (white_or_black_or_all) {
		case "White":
			filterFunction = function(pieceEntry){return pieceEntry.get(0).indexOf("W")==0;};
			break;
		case "Black":
			filterFunction = function(pieceEntry){return pieceEntry.get(0).indexOf("B")==0;};
			break;
		default:
			filterFunction = function(){return true;}
	}
	
	
	return new Calculator(
		white_or_black_or_all + " AveragePieceValue",
		function(board) {
			var pList = board.exportPiecesToList();
			// ... and filter it
			var pieceList = [];
			for (var i=0; i<pList.size(); i++) {
				if (filterFunction(pList.get(i), board)) {
					pieceList.push(pList.get(i));
				}
			}
			
			return copyEvalFunc(board)/pieceList.length;
			// There's another way to get size with copyFunc.apply, or .call or something
			// And then copyFunc.pieceList.length (since I't already calculated)
			// But I don't know off the top of my head w/o studying
		}
	);
};


var Symmetry = function(reflect_or_rotate) {
	return new Calculator(
		"StraightUpSymmetry" + " " + reflect_or_rotate,
		function(board) {
			pList = board.exportPiecesToList();
			total_possible_symmetries = 0;	// will equal white pieces
			total_actual_symmetries_reflect = 0;	// will equal black pieces that reflect across x axis
			total_actual_symmetries_rotate = 0;	// will equal black pieces that rotate around middle
			for (var i=0; i<pList.size(); i++) {
				var pieceEntry = pList.get(i);
				if (pieceEntry.get(0).indexOf("W")==0) {   // if white
					total_possible_symmetries ++;
					for (var j=0; j<pList.size(); j++) {	// look for a match
						var cpEntry = pList.get(j);
						if (cpEntry.get(0).indexOf("B")==0) {    // if black
							if ((cpEntry.get(2).intValue()+pieceEntry.get(2).intValue())/2==3.5) {  // if same location from y-axis middle
								if (reflect_or_rotate == 'reflect') {
									if (cpEntry.get(1).intValue()==pieceEntry.get(1).intValue()) {   // if location is reflection
										total_actual_symmetries_reflect++;
										break;
									}
								}
								else if (reflect_or_rotate == 'rotate') {
									if ((cpEntry.get(1).intValue()+pieceEntry.get(1).intValue())/2==3.5) {   // if location is rotation
										total_actual_symmetries_rotate++;
										break;
									}
								}
							}
						}
					}
				}
			}
			if (reflect_or_rotate == 'reflect') {
				return total_actual_symmetries_reflect/total_possible_symmetries;
			}
			else if (reflect_or_rotate == 'rotate') {
				return total_actual_symmetries_rotate/total_possible_symmetries;
			}
			else {throw new Error("Bad name")}
		}
	);
};


var TotalisticUnweightedCenter = function(dir) {
	return CenterOfMass(
		dir,
		undefined,
		undefined,
		'Total' + dir + 'CoorUnweigthedCenter'
	);
};

var TotalisticWeightedCenter = function(dir) {
	return CenterOfMass(
		dir,
		function(pType){
			var pDict = {
				"P": 1,		// Pawn
				"B": 3,		// Bishop
				"N": 3,		// Knight
				"R": 7,		// Rook
				"Q": 10,	// Queen
				"K": 10		// King
			};
			if (pDict[pType.substring(1)] == undefined) {
				throw new Error("Unknown Piece:\t->" + pType + "<-");
			}
			return pDict[pType.substring(1)];
		},
		undefined,
		'Total' + dir + 'CoorWeigthedCenter'
	);
};

var PieceCountVars = function(white_or_black_or_all, weighted_or_unweighted) {
	// Determine what we are looking for, black, white, or total
	var filterFunction;
	switch (white_or_black_or_all) {
		case "White":
			filterFunction = function(pieceEntry, board){return pieceEntry.get(0).indexOf("W")==0;};
			break;
		case "Black":
			filterFunction = function(pieceEntry, board){return pieceEntry.get(0).indexOf("B")==0;};
			break;
		default:
			// Leave it as undefined
	}
	
	// And are we weighted?
	var pieceWeight = function(){return 1};
	if (weighted_or_unweighted == "Weighted") {
		pieceWeight = function(pType){
			var pDict = {
				"P": 1,		// Pawn
				"B": 3,		// Bishop
				"N": 3,		// Knight
				"R": 7,		// Rook
				"Q": 10,	// Queen
				"K": 10		// King
			};
			if (pDict[pType.substring(1)] == undefined) {
				throw new Error("Unknown Piece:\t->" + pType + "<-");
			}
			return pDict[pType.substring(1)];
		}
	}
	
	return PieceCount(
		pieceWeight,
		filterFunction,
		white_or_black_or_all + weighted_or_unweighted + 'Count'
	);
};



//*************************GameLooper.js*****************************

function GameLooper(initialGame, calcList) {
	/*
	 To do:
	 research whether to use undefined or null
	*/
	
	//----------------------Attributes----------------------
	
	// reference variable to the current "Game" under analysis
	this.currentGame = null;
	if (arguments.length > 0) {
		this.currentGame = initialGame;
	}
	
	// keep track of which calculators apply to this GL
	this.calcs = [];
	if (arguments.length > 1) {
		// only the driver should really be doing this, so don't worry about Java arrays at all
		this.calcs = calcList.splice();	// Array.splice() is a copy
	}
	
	//------------------------Methods------------------------
	
	// Open a Game so that we can start dealing with it. Throw an {ErrorNameHere} error if another Game is already open.
	this.open = function(game) {
		if (this.currentGame != null) {
			throw new Error("GameLooper - AlreadyInUse");
		}
		this.currentGame = game;
		for (var i in this.calcs) {
			this.calcs[i].reset();
		}
	}

	// Close the currentGame so another Game can be used
	this.close = function() {
		if (this.currentGame == null) {
			//already closed/never opened
			throw new Error("GameLooper - AlredyClosed");
		}
		this.currentGame = null;
	}
	
	// addCalculator() makes each GameLooper different. Calculates different stuff
	this.addCalculator = function(oneCalc) {
		if (!(oneCalc instanceof Calculator) && !(oneCalc instanceof Packages.org.samjoey.calculator.JCalculator)) {
			// not a real Calculator
			throw new TypeError("GameLooper - NotABonafideCalc");
		}
		oneCalc.reset();
		this.calcs.push(oneCalc);
	}
	
	// this is like the most important part
	// calculate() takes the current game
	// and edits it's internal HashMap
	// by appending data collected for each Calculator
	this.calculate = function() {
		// for each board that we can understand
		try {
			while (true) {   // ignorant of how many boards there actually are
				var currentBoard = this.currentGame.next();
				
				// use every tool (Calculator) we have
				for (var i=0; i<this.calcs.length; i++) {
					var calc = this.calcs[i];
					// do the calculation and push it to the curent Game's list
					currentGame.addVariable(calc.name, calc.evaluate(currentBoard));
				}
			}
		}
		catch(e if e.javaException instanceof java.util.NoSuchElementException) {  // is e.message correct?
			// Do nothing, this is expected
			// We are done with this game
		}
		finally {
			//print("Done calculating with this Game");
		}
	}
	
	// returns and prints the current variable data about the current Game
	this.read = function() {
		// Check if we can actually read a Game
		if (this.currentGame == null) {
			throw new /*Reference*/Error("GameLooper - NoGameOpenedToRead");
		}
		
		// Header
		print("Reading Game: " + currentGame.getId());
		
		// for every var/calculator we track ...
		for (var i=0; i<this.calcs.length; i++) {
			var calcName = this.calcs[i].name;
			
			// ... print out what the data is
			print("\tCalculator:" + calcName);
			// if it is there
			// prints null if entry is not found
			print("\t" + currentGame.getVarData().get(calcName));
		}
		
		// Return our Game's HashMap
		return currentGame.getVarData();
	}
}




//*************************driver.js*****************************

print("Hello, World!");

importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
importPackage(Packages.org.samjoey.calculator);
importClass(Packages.org.samjoey.graphing.GraphUtility);


// in JS, use arguments vs args
var CLIArgs = {
	'fileLoc': "",
	'verbose': false,
	'graphs': true
};

for (var i in arguments) {
	var arg = arguments[i];
	switch (true) {
		// Verbose
		case (arg.indexOf('-v')==0 || arg.indexOf('--verbose')==0 || arg.indexOf('/v')==0):
			if(arg.indexOf(':')>1) {	// param, like this "$ ./driver.js -v:false"
				CLIArgs['verbose'] = (arg.substring(arg.indexOf(':')+1)).toLowerCase() === 'true';	// cast parameter to boolean, false is default
			}
			else {		// no param, like this "$ ./driver.js -v"
				CLIArgs['verbose'] = true;
			}
			break;
		// Graphs
		case (arg.indexOf('-g')==0 || arg.indexOf('--graphs')==0 || arg.indexOf('/g')==0):
			if(arg.indexOf(':')>1) {	// param, like this "$ ./driver.js -g:false"
				CLIArgs['graphs'] = (arg.substring(arg.indexOf(':')+1)).toLowerCase() === 'true';	// cast parameter to boolean, false is default
			}
			else {		// no param, like this "$ ./driver.js -g"
				CLIArgs['graphs'] = true;
			}
			break;
		default:
			if (i == arguments.length-1) {	// if last argument
				file = new java.io.File(arg);
				if (file.exists()) {
					// allows for argument to be non-absolute
					CLIArgs['fileLoc'] += file.getCanonicalPath();
					break;
				}
				else {
					throw new Error("Not a valid file");
				}
			}
			throw new Error("Unknown argument -->" + arg + "<--");
	}
}


// Retrieve an ArrayList of Games to analyze
var gameList = Parser.parseGames(CLIArgs['fileLoc']);
// Get access to a GameLooper
var gameLooper = new GameLooper();
/*gameLooper.addCalculator(TotalisticUnweightedCenter('x'));
gameLooper.addCalculator(TotalisticWeightedCenter('x'));
//gameLooper.addCalculator(PieceCountVars(white_or_black_or_all, weighted_or_unweighted));
var calcType = {
	'Players': ["White", "Black", "Total"],
	'Weights': ["Weighted", "Unweighted"]
};
for (i in calcType['Players']) {
	gameLooper.addCalculator(AveragePieceValue(calcType['Players'][i]));
	for (j in calcType['Weights']) {
		gameLooper.addCalculator(PieceCountVars(calcType['Players'][i], calcType['Weights'][j]));
	}
}*/
gameLooper.addCalculator(new JCalculatorChecks("Black"));
gameLooper.addCalculator(new JCalculatorChecks("White"));
gameLooper.addCalculator(new JCalculatorMoveTime(""));
gameLooper.addCalculator(new JCalculatorMoveTime("White"));
gameLooper.addCalculator(new JCalculatorMoveTime("Black"));
gameLooper.addCalculator(new JCalculatorPawnMovement());
gameLooper.addCalculator(new JCalculatorMoveDistance());
gameLooper.addCalculator(new JCalculatorWhiteMoveDistance());
gameLooper.addCalculator(new JCalculatorBlackMoveDistance());
gameLooper.addCalculator(Symmetry("reflect"));
gameLooper.addCalculator(Symmetry("rotate"));
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
	if (CLIArgs['verbose']) {
		print("-----------------------------------");
		gameLooper.read();
	}
	
	// prepare to move on to the next
	gameLooper.close();
	
}

// Do the graphical stuff in a new thread
if (CLIArgs['graphs']) {
	(new java.lang.Thread(
		function() {	    // For notation check Rhino documentation. JavaScript Functions as Java Interfaces
			GraphUtility.createGraphs(gameList);
		}
	)).start();
}

print('Done');


