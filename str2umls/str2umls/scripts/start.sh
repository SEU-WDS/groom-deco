#!/usr/bin/env bash

BASEDIR=/home/stu/MetaMap/public_mm
PBJAR=$BASEDIR/src/javaapi/dist/prologbeans.jar
MMAJAR=$BASEDIR/src/javaapi/dist/MetaMapApi.jar
PROJAR=/home/stu/MetaMap/str2umls/str2umls.jar
export CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:$PBJAR:$MMAJAR
java -jar $PROJAR
echo "done!"