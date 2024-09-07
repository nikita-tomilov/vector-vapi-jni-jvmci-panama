#!/bin/bash
. env.sh
java17jvmci \
      -DbenchmarksInclude=SingleArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -Dparamcalc=NativeJvmci \
      -DresultName=SingleArrayOperations-JVMCI-JDK17 \
      -jar $BENCH17

java17jvmci \
      -DbenchmarksInclude=MultiArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=MICROSECONDS \
      -Dparamdimension=256,384,768 \
      -DparamvectorsCount=100,250,500 \
      -Dparamcalc=NativeJvmci \
      -DresultName=MultiArrayOperations-JVMCI-JDK17 \
      -jar $BENCH17
