engine.eval(new java.io.FileReader(calcLoc));
importClass(Packages.org.samjoey.calculator.JCalculator);
importClass(Packages.org.samjoey.model.Game);

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
	calcs = [];
	if (arguments.length > 1) {
		// only the driver should really be doing this, so don't worry about Java arrays at all
		calcs = calcList.splice();	// Array.splice() is a copy
	}
	
	//------------------------Methods------------------------
	
	// Open a Game so that we can start dealing with it. Throw an {ErrorNameHere} error if another Game is already open.
	this.open = function(game) {
		if (this.currentGame != null) {
			throw new Error("GameLooper - AlreadyInUse");
		}
		this.currentGame = game;
		for (var i in calcs) {
			calcs[i].reset();
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
		calcs.push(oneCalc);
	}
	
	// this is like the most important part
	// calculate() takes the current game
	// and edits it's internal HashMap
	// by appending data collected for each Calculator
	prog = 0;
	this.calculate = function() {
        var game = this.currentGame;
        (new java.lang.Thread(
                function() {
                    
		// for each board that we can understand
		try {
			while (true) {   // ignorant of how many boards there actually are
				var currentBoard = game.next();
				
				// use every tool (Calculator) we have
				for (var i = 0; i < calcs.length; i++) {
					var calc = calcs[i];
					// do the calculation and push it to the curent Game's list
					game.addVariable(calc.name, calc.evaluate(currentBoard));
				}
			}
		}
		catch (e if e.javaException instanceof java.util.NoSuchElementException) {  // is e.message correct?
			// Do nothing, this is expected
			// We are done with this game
		}
		finally {
                        //print("Game: " + game.getId() + " is done. " + (prog + 1) + "\n");
                        prog ++;
                        engine.put("progress", "" + prog);
		}
                    return;
                })).start();
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
		for (var i = 0; i < calcs.length; i++) {
			var calcName = calcs[i].name;
			
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