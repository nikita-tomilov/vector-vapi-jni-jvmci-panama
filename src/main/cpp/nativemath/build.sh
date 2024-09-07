#!/bin/bash

if [ ! -z "$1" ]; then
    cd $1
fi

javac -h . ../../java/ru/ifmo/se/jni/NativeMathJni.java
rm -rf ../../java/ru/ifmo/se/jni/NativeMathJni.class

TMPDIR=./cmake-build-debug

rm -rf $TMPDIR
mkdir -p $TMPDIR
cd cmake-build-debug || return
cmake -DCMAKE_BUILD_TYPE=Release ../
cmake --build .
cd ..

mkdir -p ../../resources/
mv dist/libnativemath.so ../../resources/
rm -rf dist

/opt/jextract-22/bin/jextract nativemath.h
sed -i '1s/^/import java.lang.foreign.Linker.Option;\n/' nativemath_h.java
sed -i '1s/^/package ru.ifmo.se.panama;\n/' nativemath_h.java
sed -i 's!downcallHandle(ADDR, DESC);!downcallHandle(ADDR, DESC, Option.critical(true));!g' nativemath_h.java
mv nativemath_h.java ../../java/ru/ifmo/se/panama/