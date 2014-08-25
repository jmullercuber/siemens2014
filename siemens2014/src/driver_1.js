//#!/usr/bin/env rhino

print("Hello, World!");

//importClass(Packages.org.model.Board);   // driver shouldn't interact with Boards
importClass(Packages.org.samjoey.parse.Parser);
importClass(Packages.org.samjoey.model.Game);
importPackage(Packages.org.samjoey.calculator);
importClass(Packages.org.samjoey.pattern.PatternFinder);
importClass(Packages.org.samjoey.samples.GraphicalViewer);
importClass(Packages.org.samjoey.graphing.GraphUtility);
engine.eval(new java.io.FileReader(defsLoc));
engine.eval(new java.io.FileReader(loopLoc));


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
        case (arg.indexOf('-v') == 0 || arg.indexOf('--verbose') == 0 || arg.indexOf('/v') == 0):
            if (arg.indexOf(':') > 1) {	// param, like this "$ ./driver.js -v:false"
                CLIArgs['verbose'] = (arg.substring(arg.indexOf(':') + 1)).toLowerCase() === 'true';	// cast parameter to boolean, false is default
            }
            else {		// no param, like this "$ ./driver.js -v"
                CLIArgs['verbose'] = true;
            }
            break;
            // Graphs
        case (arg.indexOf('-g') == 0 || arg.indexOf('--graphs') == 0 || arg.indexOf('/g') == 0):
            if (arg.indexOf(':') > 1) {	// param, like this "$ ./driver.js -g:false"
                CLIArgs['graphs'] = (arg.substring(arg.indexOf(':') + 1)).toLowerCase() === 'true';	// cast parameter to boolean, false is default
            }
            else {		// no param, like this "$ ./driver.js -g"
                CLIArgs['graphs'] = true;
            }
            break;
        default:
            if (i == arguments.length - 1) {	// if last argument
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
}
gameLooper.addCalculator(new JCalculatorChecks("Black"));
gameLooper.addCalculator(new JCalculatorChecks("White"));
gameLooper.addCalculator(new JCalculatorMoveTime(""));
gameLooper.addCalculator(new JCalculatorMoveTime("White"));
gameLooper.addCalculator(new JCalculatorMoveTime("Black"));
gameLooper.addCalculator(new JCalculatorPawnMovement());
//gameLooper.addCalculator(new JCalculatorMoveDistance(""));
//gameLooper.addCalculator(new JCalculatorMoveDistance("Black"));
//gameLooper.addCalculator(new JCalculatorMoveDistance("White"));
gameLooper.addCalculator(new JCalculatorMoveDistance());*/
gameLooper.addCalculator(new JCalculatorWhiteMoveDistance());
gameLooper.addCalculator(new JCalculatorBlackMoveDistance());
//gameLooper.addCalculator(Symmetry("reflect"));
//gameLooper.addCalculator(Symmetry("rotate"));
// gameLooper.addCalculator(<calcNameHere>);


// For every game...
for (var i = 0; i < gameList.size(); i++) {
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
