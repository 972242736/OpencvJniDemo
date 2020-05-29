# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#引入opencv头文件
include_directories(../jniLibs/include)

#添加动态库 预编译库是已经构建好的，使用 IMPORTED 标志去告诉 CMake
add_library(libopencv_java3 SHARED IMPORTED)
#指定库的路径
set_target_properties(libopencv_java3 PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libopencv_java3.so)

#将源码编译进本地库
add_library( # Sets the name of the library.
        opencv_jni

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        opencv_jni.cpp)

#set_target_properties(imageprocessing PROPERTIES IMPORTED_LOCATION
#        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/imageprocessing.so)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
#查找本地库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log
        android)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
#链接库 将预构建库与你本地库相关联
target_link_libraries( # Specifies the target library.
        opencv_jni
        libopencv_java3
        android
        jnigraphics
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})