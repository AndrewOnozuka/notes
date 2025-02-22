# CMakeList.txt : Top-level CMake project file, do global configuration
# and include sub-projects here.
#
cmake_minimum_required (VERSION 3.8)

project ("CSE167_FA22_HW1")

# Add sources
set(
	SOURCES_HELLO_SQUARE2
	src/Shader.cpp
	src/HelloSquare2.cpp
)
set(
	SOURCES_MANDELBROT
	src/Shader.cpp
	src/Mandelbrot.cpp
)

# Add executables
add_executable(HelloSquare2 ${SOURCES_HELLO_SQUARE2})
add_executable(Mandelbrot ${SOURCES_MANDELBROT})

# Include headers
set(
	INCLUDE_DIRECTORIES
	${PROJECT_SOURCE_DIR}/include
)
target_include_directories(HelloSquare2 PRIVATE ${INCLUDE_DIRECTORIES})
target_include_directories(Mandelbrot PRIVATE ${INCLUDE_DIRECTORIES})

# Link libraries
set(
	LINK_DIRECTORIES
	${PROJECT_SOURCE_DIR}/lib
)
target_link_directories(HelloSquare2 PRIVATE ${LINK_DIRECTORIES})
target_link_directories(Mandelbrot PRIVATE ${LINK_DIRECTORIES})
target_link_libraries(HelloSquare2 glew32 freeglut FreeImage)
target_link_libraries(Mandelbrot glew32 freeglut FreeImage)

# Ensure .dll is with .exe
file(COPY "${LINK_DIRECTORIES}/glew32.dll" DESTINATION "${CMAKE_BINARY_DIR}")
file(COPY "${LINK_DIRECTORIES}/freeglut.dll" DESTINATION "${CMAKE_BINARY_DIR}")
file(COPY "${LINK_DIRECTORIES}/FreeImage.dll" DESTINATION "${CMAKE_BINARY_DIR}")

# Move shaders to .exe
file(COPY "${PROJECT_SOURCE_DIR}/shaders" DESTINATION "${CMAKE_BINARY_DIR}")

