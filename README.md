siemens2014
===========
Scientific calculations for competition

To run:
===========
Command Line: Linux
---------

* cd siemens2014/siemens2014/src
* chmod a+x linuxRunner.sh
* ./linuxRunner.sh "relative/path/to/data.pgn"


Command Line: Windows
---------
* cd siemens2014\siemens2014\src
* windowsRunner.bat "relative\path\to\data.pgn"  

Note: no longer need to use absolute path


GUI: Linux and Windows
---------
1. Open a file one of three ways:
  * Press the 'Parse' button and select a file.
  * Navigate to the 'Open' menu under 'File' and select a file.
  * Press Ctrl+O and select a file.
2. If you selected a .pgn file, follow this:
  1. The program will now parse the entire .pgn file. The parsing itself will not take long (relatively), but the calculating can take between 20-45 seconds per game. Plan accordingly.
  2. After this is done, you may view the games, their data and their graphs.
  3. To change games, press the double arrow buttons or press Ctrl+G and enter a number.
  4. To change turns, press the single arrow buttons or press Ctrl+P and enter a number.
  5. To find patterns, press the 'Find Patterns' button.
      * Note: This will output in the console
      * Note: This is not a done system
  6. To output to a .jsca file, press 'Write To File'
3. If you selected a .jsca file, follow this:
  1. The program will now take a couple of seconds to do a preliminary parse.
  2. After this is done, you can follow 2-5 under the .pgn instructions.
  3. Additionally, under the game menu, you can select to view graphs only for certain games by entering the game IDs as comma separated values.


.JSCA Format Guide
==========
Fun fact: .JSCA stands for <b>J</b>oey <b>S</b>am <b>C</b>hess <b>A</b>nalysis.


Tags
----------
With the exception of variable and move tags, all tags are formatted as such:
```
[Tag Name "Value"]
```
Variable tags and move tags ommit the quotation marks and instead have values enclosed within square brackets with values separated by commas.


Required Tags
----------
This is a list of all required tags and their definition:
* **GameID** - an integer that is assigned to each game as an identifier. This *must* be unique.
* **WinType** - a string describing *how* the winner won (not who) (can be draw).
* **Winner** - an integer (0, 1, or 2) identifing who won.
* **WhiteTime** - how much time white took (double).
* **BlackTime** - how much time black took (double).
* **TimeAllowed** - how much time was allotted to each player.
* **PlyCount** - the total number of plies.


Variable Tags
---------
A variable tag's name is the type of variable. Instead of a value inside of quotation marks, a variable tag's data is within two square brackes as comma separated values. Example:
```
[WhitePieceCount [16, 16, 16, 15, 15, 0]]
```
There are no requirements on quantity or type of variables.
Note: moves are considered variables and **must** be put last followed by a blank line.
