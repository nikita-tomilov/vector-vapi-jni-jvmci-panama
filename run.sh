#!/bin/bash
. env.sh
#java22panama \
#      -DbenchmarksInclude=SingleArrayOperations \
#      -Dmode=AverageTime \
#      -DtimeUnit=NANOSECONDS \
#      -Dparamdimension=256,384,768 \
#      -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
#      -DresultName=SingleArrayOperations \
#      -jar $BENCH

#java17jvmci \
#      -DbenchmarksInclude=MultiArrayOperations \
#      -Dmode=AverageTime \
#      -DtimeUnit=NANOSECONDS \
#      -Dparamdimension=256,384,768 \
#      -DparamvectorsCount=100,250,500 \
#      -DresultName=MultiArrayOperations \
#      -jar $BENCH

java22panama \
      -DbenchmarksInclude=MultiArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -Dparamcalc=NativePanama \
      -DresultName=SingleArrayOperations \
      -jar $BENCH