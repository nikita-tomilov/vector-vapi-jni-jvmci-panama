#!/bin/bash
. env.sh

# JVMCI library Nalim does not work with JDK 22, downgrading to 17
java17jvmci \
      -DbenchmarksInclude=SingleVectorOperationsBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -Dparamcalc=NativeJvmci \
      -DresultName=SingleVectorOperationsBenchmarkResults-JDK17 \
      -jar $BENCH17

java17jvmci \
      -DbenchmarksInclude=MultiVectorOperationsBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=MICROSECONDS \
      -Dparamdimension=256,384,768 \
      -DparamvectorsCount=100,250,500 \
      -Dparamcalc=NativeJvmci \
      -DresultName=MultiVectorOperationsBenchmarkResults-JDK17 \
      -jar $BENCH17

java17jvmci \
      -DbenchmarksInclude=KnnBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=MILLISECONDS \
      -Dparamcalc=NativeJvmci \
      -DresultName=KnnBenchmarkResults-JDK17 \
      -jar $BENCH17

java17jvmci \
      -DbenchmarksInclude=ProductQuantizationBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=SECONDS \
      -Dparamcalc=NativeJvmci \
      -DresultName=ProductQuantizationBenchmarkResults-JDK17 \
      -jar $BENCH17

java22panama \
      -DbenchmarksInclude=SingleVectorOperationsBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
      -DresultName=SingleVectorOperationsBenchmarkResults-JDK22 \
      -jar $BENCH22

java22panama \
      -DbenchmarksInclude=MultiVectorOperationsBenchmark \
      -Dmode=AverageTime \
      -DtimeUnit=MICROSECONDS \
      -Dparamdimension=256,384,768 \
      -DparamvectorsCount=100,250,500 \
      -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
      -DresultName=MultiVectorOperationsBenchmarkResults-JDK22 \
      -jar $BENCH22

java22panama \
     -DbenchmarksInclude=KnnBenchmark \
     -Dmode=AverageTime \
     -DtimeUnit=MILLISECONDS \
     -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
     -DresultName=KnnBenchmarkResults-JDK22 \
     -jar $BENCH22

java22panama \
     -DbenchmarksInclude=ProductQuantizationBenchmark \
     -Dmode=AverageTime \
     -DtimeUnit=SECONDS \
     -Dparamcalc=PlainJava,VectorApi,NativeJni,NativePanama \
     -DresultName=ProductQuantizationBenchmarkResults-JDK22 \
     -jar $BENCH22

systemctl suspend