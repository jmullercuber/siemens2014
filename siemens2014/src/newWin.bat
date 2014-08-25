@echo off
echo %time%
java -cp .;./*;rhino/js.jar org.mozilla.javascript.tools.shell.Main driver_plus.js -g:false ..\docs\smaller.pgn
echo %time%