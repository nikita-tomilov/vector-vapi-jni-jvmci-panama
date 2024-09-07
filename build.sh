#!/bin/bash
. env.sh
rm *.jar

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

# JVMCI library Nalim does not work with JDK 22, downgrading to 17 and removing the Panama sources for 22
sed -i 's!<jvm.version>22</jvm.version>!<jvm.version>17</jvm.version>!' pom.xml
mv src/main/java/ru/ifmo/se/panama/nativemath_h.java /tmp
mv src/main/java/ru/ifmo/se/calculators/NativePanamaCalculator.java /tmp
./mvnw clean package
mv $JAR_FILE $BENCH17

# Upgrading back to 22 and restoring the Panama sources
export JAVA_HOME=$JAVA_22_HOME
sed -i 's!<jvm.version>17</jvm.version>!<jvm.version>22</jvm.version>!' pom.xml
mv /tmp/nativemath_h.java src/main/java/ru/ifmo/se/panama/
mv /tmp/NativePanamaCalculator.java src/main/java/ru/ifmo/se/calculators/
./mvnw clean package
mv $JAR_FILE $BENCH22