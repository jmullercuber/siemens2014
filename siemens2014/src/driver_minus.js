//*************************Calculator.js*****************************


function Calculator(nm, func, rst) {
	this.name = nm;
	this.evaluate = func;
	this.reset = rst || function(){};
}


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
//gameLooper.addCalculator(Symmetry("reflect"));
//gameLooper.addCalculator(Symmetry("rotate"));
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


