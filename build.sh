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

sed -i 's!<jvm.version>22</jvm.version>!<jvm.version>17</jvm.version>!' pom.xml
./mvnw clean package
mv $JAR_FILE $BENCH17

export JAVA_HOME=$JAVA_22_HOME
sed -i 's!<jvm.version>17</jvm.version>!<jvm.version>22</jvm.version>!' pom.xml
./mvnw clean package
mv $JAR_FILE $BENCH22