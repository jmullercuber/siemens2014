#! /bin/bash
dir=$(dirname $0)
export CLASSPATH=${CLASSPATH}${CLASSPATH:+:}.:..:${dir}/rhino/\*
echo using CLASSPATH=$(printenv CLASSPATH) > /dev/stderr
#[ "$1" = "-f" ] || { RLWRAP="rlwrap -C js" ;}
#exec $RLWRAP java ${JAVAOPTS} \
exec java ${JAVAOPTS} \
org.mozilla.javascript.tools.shell.Main driver.js "$@"
