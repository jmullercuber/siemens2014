/*
 * Here place definitions for various pre-made Calculators
 * These names will be imported to the driver, and will
 * have local meaning there.
 * Purpose: To not culter up that precious driver file 
 */

load ('org/samjoey/calculator/Calculator.js');


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
							if ((cpEntry.get(2).intValue()+pieceEntry.get(2).intValue
())/2==3.5) {  // if same location from y-axis middle
								if (reflect_or_rotate == 'reflect') {
									if (cpEntry.get(1).intValue()==pieceEntry.get
(1).intValue()) {   // if location is reflection
										total_actual_symmetries_reflect++;
										break;
									}
								}
								else if (reflect_or_rotate == 'rotate') {
									if ((cpEntry.get(1).intValue()+pieceEntry.get
(1).intValue())/2==3.5) {   // if location is rotation
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


/*var PlayerWeightedCenter = new Calculator(
		function(board) {
			var translationKey = {
				'':''
			};
		}
);*/