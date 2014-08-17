/*function Calculator(func, params) {
	
}*/


function Calculator(nm, func, rst) {
	this.name = nm;
	this.evaluate = func;
	this.reset = rst || function(){};
}
