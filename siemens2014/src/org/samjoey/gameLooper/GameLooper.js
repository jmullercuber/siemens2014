load ('org/samjoey/calculator/Calculator.js');

function GameLooper(initialGame, calcList) {
	/*
	 To do:
	 research how rhino deals with undefined vs null
	 //figure out type of error open() should throw
	 	nope, from MDN I understand how to throw Error
	 implement calculate()
	 implement read() or move functionality to different class
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
		if (!(oneCalc instanceof Calculator)) {
			// not a real Calculator
			throw new TypeError("GameLooper - NotABonafideCalc");
		}
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
