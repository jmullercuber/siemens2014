#!/bin/sh
java -classpath "./rhino/*:." org.mozilla.javascript.tools.shell.Main driver.js "$@"