# CMakeList.txt : Top-level CMake project file, do global configuration
# and include sub-projects here.
#
cmake_minimum_required (VERSION 3.8)

project ("CSE167_FA22_HW0")

# Add sources
set(
	SOURCES_HELLO_GL
	src/HelloGL.cpp
)
set(
	SOURCES_HELLO_SQUARE
	src/HelloSquare.cpp
)

# Add executables
add_executable(HelloGL ${SOURCES_HELLO_GL})
add_executable(HelloSquare ${SOURCES_HELLO_SQUARE})

# Include headers
set(
	INCLUDE_DIRECTORIES
	${PROJECT_SOURCE_DIR}/include
)
target_include_directories(HelloGL PRIVATE ${INCLUDE_DIRECTORIES})
target_include_directories(HelloSquare PRIVATE ${INCLUDE_DIRECTORIES})

# Link libraries
set(
	LINK_DIRECTORIES
	${PROJECT_SOURCE_DIR}/lib
)
target_link_directories(HelloGL PRIVATE ${LINK_DIRECTORIES})
target_link_directories(HelloSquare PRIVATE ${LINK_DIRECTORIES})
target_link_libraries(HelloGL glew32 freeglut)
target_link_libraries(HelloSquare glew32 freeglut FreeImage)

# Ensure .dll is with .exe
file(COPY "${LINK_DIRECTORIES}/glew32.dll" DESTINATION "${CMAKE_BINARY_DIR}")
file(COPY "${LINK_DIRECTORIES}/freeglut.dll" DESTINATION "${CMAKE_BINARY_DIR}")
file(COPY "${LINK_DIRECTORIES}/FreeImage.dll" DESTINATION "${CMAKE_BINARY_DIR}")

