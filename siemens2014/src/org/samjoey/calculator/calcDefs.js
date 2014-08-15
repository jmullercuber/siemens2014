/*
 * Here place definitions for various pre-made Calculators
 * These names will be imported to the driver, and will
 * have local meaning there.
 * Purpose: To not culter up that precious driver file 
 */

load ('org/samjoey/calculator/Calculator.js');


// CenterOfMass is a function factory
var CenterOfMass = function(x_or_y, pieceWeigth, filterFunc, calcName) {
	// arguments
	//  In what direction shouid we calculate? 'x', or file, by default
	x_or_y = x_or_y || 'x';
	
	// How much is each piece worth? Weight each piece's value
	pieceWeigth = pieceWeigth || (function(pieceID) {return 1;});
	
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
				
				sum_mass_pos['x'] += pieceWeigth(pieceData.get(0))*pieceData.get(1);
				sum_mass_pos['y'] += pieceWeigth(pieceData.get(0))*pieceData.get(2);
				total_mass += pieceWeigth(pieceData.get(0));
			}
			
			center_of_mass = sum_mass_pos[x_or_y]/total_mass;
			return center_of_mass;
		}
	);
 };


// PieceCount is a function factory
var PieceCount = function(pieceWeigth, filterFunc, calcName) {
	// arguments
	
	// How much is each piece worth? Weight each piece's value
	pieceWeigth = pieceWeigth || (function(pieceID) {return 1;});
	
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
				
				total_mass += pieceWeigth(pieceData.get(0));
			}
			
			return total_mass;
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


var TotalisticUnweightedCount = new PieceCount(
	undefined,
	undefined,
	'TotalUnweigthedCount'
);

var TotalisticWeightedCount = new PieceCount(
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
	'TotalWeigthedCount'
);


/*var PlayerWeightedCenter = new Calculator(
		function(board) {
			var translationKey = {
				'':''
			};
		}
);*/
