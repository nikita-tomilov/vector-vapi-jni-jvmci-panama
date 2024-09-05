#!/bin/bash
. env.sh
java17jvmci \
      -DbenchmarksInclude=SingleArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -DresultName=SingleArrayOperations \
      -jar $BENCH

#java17jvmci \
#      -DbenchmarksInclude=MultiArrayOperations \
#      -Dmode=AverageTime \
#      -DtimeUnit=NANOSECONDS \
#      -Dparamdimension=256,384,768 \
#      -DparamvectorsCount=100,250,500 \
#      -DresultName=MultiArrayOperations \
#      -jar $BENCH
