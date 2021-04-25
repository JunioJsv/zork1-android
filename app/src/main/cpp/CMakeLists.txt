# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("zork")

#add_definitions(-DMORE_NONE)
#set(CMAKE_C_FLAGS "-Wall -g")
# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#add_library( # Sets the name of the library.
#        native-lib
#
#        # Sets the library as a shared library.
#        SHARED
#
#        # Provides a relative path to your source file(s).
#        native-lib.c)

#add_library(zork-lib SHARED
#        zork/actors.c zork/ballop.c zork/clockr.c zork/demons.c zork/dgame.c zork/dinit.c
#        zork/dmain.c zork/dso1.c zork/dso2.c zork/dso3.c zork/dso4.c zork/dso5.c zork/dso6.c
#        zork/dso7.c zork/dsub.c zork/dverb1.c zork/dverb2.c zork/gdt.c zork/lightp.c zork/local.c
#        zork/nobjs.c zork/np.c zork/np1.c zork/np2.c zork/np3.c zork/nrooms.c zork/objcts.c
#        zork/rooms.c zork/sobjs.c zork/supp.c zork/sverbs.c zork/verbs.c zork/villns.c)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
#find_library( # Sets the name of the path variable.
#        log-lib
#
#        # Specifies the name of the NDK library that
#        # you want CMake to locate.
#        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

#target_link_libraries( # Specifies the target library.
#        native-lib
#        # Links the target library to the log library
#        # included in the NDK.
#        ${log-lib}
#        zork-lib)