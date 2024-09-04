cmake_minimum_required(VERSION 3.15)
project(nativemath)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/dist)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/dist)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "-Wall -Wextra")
set(CMAKE_CXX_FLAGS_DEBUG "-g")
set(CMAKE_CXX_FLAGS_RELEASE "-Ofast -march=native")

find_package(JNI)
include_directories(${JNI_INCLUDE_DIRS})

add_library(nativemath SHARED nativemath.cpp)

target_include_directories(nativemath PUBLIC ./)

target_link_libraries(nativemath ${CMAKE_DL_LIBS})
