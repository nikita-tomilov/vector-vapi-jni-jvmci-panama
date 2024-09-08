#!/bin/bash
. env.sh

# JVMCI library Nalim does not work with JDK 22, downgrading to 17
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

java17jvmci \
      -DbenchmarksInclude=ComplexVectorOperations \
      -Dmode=AverageTime \
      -DtimeUnit=MILLISECONDS \
      -Dparamcalc=NativeJvmci \
      -DresultName=ComplexVectorOperations-JDK17 \
      -jar $BENCH17

java22panama \
      -DbenchmarksInclude=SingleArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
      -DresultName=SingleArrayOperations-JDK22 \
      -jar $BENCH22

java22panama \
      -DbenchmarksInclude=MultiArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=MICROSECONDS \
      -Dparamdimension=256,384,768 \
      -DparamvectorsCount=100,250,500 \
      -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
      -DresultName=MultiArrayOperations-JDK22 \
      -jar $BENCH22

java22panama \
     -DbenchmarksInclude=ComplexVectorOperations \
     -Dmode=AverageTime \
     -DtimeUnit=MILLISECONDS \
     -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
     -DresultName=ComplexVectorOperations-JDK22 \
     -jar $BENCH22