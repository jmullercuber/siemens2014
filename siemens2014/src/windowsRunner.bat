@echo off
java -cp ./*;./rhino/* org.mozilla.javascript.tools.shell.Main driver.js %* %~dp0%