/*
 * Here place definitions for various pre-made Calculators
 * These names will be imported to the driver, and will
 * have local meaning there.
 * Purpuse: To not culter up that precious driver file 
 */

load ('/org/samjoey/calculator/Calculator.js');


// CenterOfMass is a function factory
var CenterOfMass = function(x_or_y, pieceWeigth, filterFunc) {
    // arguments
    //  In what direction shouid we calculate? 'x', or file, by default
    x_or_y = x_or_y || 'x'
    
    // How much is each piece worth? Weight each piece's value
    pieceWeigth = pieceWeigth || (pieceID => 1);
    
    // What types of pieces do we want? Filter the list
    filterFunc = filterFunct || (pieceEntry => true);
    
    return new Calculator(
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
            for (var i=0; i<pieceList.size(); i++) {
                var pieceData = pieceList.get(i);
                
                sum_mass_pos['x'] += pieceWeigth(pieceData.get(0))*pieceData.get(1);
                sum_mass_pos['y'] += pieceWeigth(pieceData.get(0))*pieceData.get(2);
                total_mass += pieceWeigth(pieceData.get(0));
            }
            
            center_of_mass = sum_mass_pos[x_or_y]/total_mass;
            return center_of_mass;
        }
    );
 };


var TotalisticXUnweightedCenter = CenterOfMass('x');


/*var PlayerWeightedCenter = new Calculator(
        function(board) {
            var translationKey = {
                '':''
            };
        }
);*/