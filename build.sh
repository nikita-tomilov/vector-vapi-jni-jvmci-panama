#!/bin/bash
. env.sh

export JAVA_HOME=$JAVA_17_HOME
# wget https://github.com/apangin/nalim/releases/download/v1.1/nalim.jar
# ./mvnw install:install-file \
#   -Dfile=nalim.jar \
#   -DgroupId=one.nalim \
#   -DartifactId=nalim \
#   -Dversion=1.1 \
#   -Dpackaging=jar \
#   -DgeneratePom=true
# rm nalim.jar

./mvnw clean package
mv $JAR_FILE $BENCH17