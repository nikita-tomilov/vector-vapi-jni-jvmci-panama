#!/bin/bash

if [ ! -z "$1" ]; then
    cd $1
fi

# Generating JNI headers
javac -h . ../../java/ru/ifmo/se/jni/NativeMathJni.java
rm -rf ../../java/ru/ifmo/se/jni/NativeMathJni.class

# Building the native library
TMPDIR=./cmake-build-debug
rm -rf $TMPDIR
mkdir -p $TMPDIR
cd cmake-build-debug || return
cmake -DCMAKE_BUILD_TYPE=Release ../
cmake --build .
cd ..

# Moving the native library to the resources directory
mkdir -p ../../resources/
mv dist/libnativemath.so ../../resources/
rm -rf dist

# Generating Panama bindings based on header file without "extern" declarations
sed 's/extern.*$//g' nativemath.h > /tmp/nativemath.h
/opt/jextract-22/bin/jextract /tmp/nativemath.h
rm /tmp/nativemath.h

# Fixing the generated bindings to use critical sections
sed -i '1s/^/import java.lang.foreign.Linker.Option;\n/' nativemath_h.java
sed -i '1s/^/package ru.ifmo.se.panama;\n/' nativemath_h.java
sed -i 's!downcallHandle(ADDR, DESC);!downcallHandle(ADDR, DESC, Option.critical(true));!g' nativemath_h.java

# Moving the generated bindings to the correct directory
mkdir -p ../../java/ru/ifmo/se/panama/
mv nativemath_h.java ../../java/ru/ifmo/se/panama/