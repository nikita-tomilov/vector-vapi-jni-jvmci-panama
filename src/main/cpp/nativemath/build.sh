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

mv dist/libnativemath.so ../../resources/
rm -rf dist