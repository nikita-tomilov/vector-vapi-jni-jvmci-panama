#!/bin/bash
. env.sh

# wget https://github.com/apangin/nalim/releases/download/v1.1/nalim.jar
# ./mvnw install:install-file \
#   -Dfile=nalim.jar \
#   -DgroupId=one.nalim \
#   -DartifactId=nalim \
#   -Dversion=1.1 \
#   -Dpackaging=jar \
#   -DgeneratePom=true
# rm nalim.jar
#

./mvnw clean package
