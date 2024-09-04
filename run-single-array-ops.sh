#!/bin/bash
. env.sh
java17jvmci \
      -DbenchmarksInclude=SingleArrayOperations \
      -Dmode=AverageTime \
      -DtimeUnit=NANOSECONDS \
      -Dparamdimension=256,384,768 \
      -jar $BENCH
