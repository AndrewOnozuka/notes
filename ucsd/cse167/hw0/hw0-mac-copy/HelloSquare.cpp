#include <iostream>
#ifdef __APPLE__
#include <OpenGL/gl3.h>
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
#define GLM_FORCE_RADIANS
#include <glm/glm.hpp>
#include "Screenshot.h"

static const int width = 500;
static const int height = 500;
static const char* title = "Hello Cat";
static GLuint program; // Shader program
static GLuint cat_vao;
static GLuint buffers[2];

void printHelp() {
    std::cout << R"(
    Available commands:
      press 'h' to print this message again.
      press Esc to quit.
      press 'o' to save a screenshot.
)";
}

void initialize(void) {
    printHelp();
    glClearColor(0.0, 0.2, 0.5, 0.0); // Background color
    glViewport(0, 0, width, height);

    // Define the cat shape
    GLfloat positions[] = {
        -0.625f,  0.75f,   // Vertex 0: Top left ear
        -0.25f,   0.25f,   // Vertex 1: Left inner ear
         0.25f,   0.25f,   // Vertex 2: Right inner ear
         0.625f,  0.75f,   // Vertex 3: Top right ear
        -0.625f, -0.625f,  // Vertex 4: Bottom left
         0.625f, -0.625f   // Vertex 5: Bottom right
    };

    GLfloat colors[] = {
        1.0f, 0.0f, 0.0f,  // Vertex 0: Red
        0.0f, 1.0f, 0.0f,  // Vertex 1: Green
        0.0f, 0.0f, 1.0f,  // Vertex 2: Blue
        1.0f, 1.0f, 0.0f,  // Vertex 3: Yellow
        0.0f, 1.0f, 1.0f,  // Vertex 4: Cyan
        1.0f, 0.0f, 1.0f   // Vertex 5: Magenta
    };

    GLuint indices[] = {
        0, 1, 4,  // Triangle 1: Left ear to bottom left
        1, 2, 4,  // Triangle 2: Left inner to bottom left
        2, 5, 4,  // Triangle 3: Right inner to bottom left
        2, 3, 5   // Triangle 4: Right ear to bottom right
    };

    // Set up geometry (VAO, VBO, EBO)
    glGenVertexArrays(1, &cat_vao);
    glGenBuffers(2, buffers);

    glBindVertexArray(cat_vao);

    // Vertex positions
    glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(positions), positions, GL_STATIC_DRAW);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 2, GL_FLOAT, GL_FALSE, 0, (void*)0);

    // Vertex colors
    glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(colors), colors, GL_STATIC_DRAW);
    glEnableVertexAttribArray(1);
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, (void*)0);

    // Element indices
    GLuint ebo;
    glGenBuffers(1, &ebo);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);

    glBindVertexArray(0);

    // Vertex shader
    const char* vertShaderSrc = R"(
        #version 330 core
        layout (location = 0) in vec2 pos;
        layout (location = 1) in vec3 color;

        out vec3 vertexcolor;

        void main() {
            gl_Position = vec4(pos.x, pos.y, 0.0f, 1.0f);
            vertexcolor = color;
        }
    )";

    // Fragment shader
    const char* fragShaderSrc = R"(
        #version 330 core
        in vec3 vertexcolor;

        out vec4 fragColor;

        void main() {
            fragColor = vec4(vertexcolor, 1.0f);
        }
    )";

    // Compile shaders
    GLuint vs = glCreateShader(GL_VERTEX_SHADER);
    GLuint fs = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(vs, 1, &vertShaderSrc, NULL);
    glShaderSource(fs, 1, &fragShaderSrc, NULL);
    glCompileShader(vs);
    glCompileShader(fs);

    // Create and link shader program
    program = glCreateProgram();
    glAttachShader(program, vs);
    glAttachShader(program, fs);
    glLinkProgram(program);
    glValidateProgram(program);

    glDeleteShader(vs);
    glDeleteShader(fs);
}

void display(void) {
    glClear(GL_COLOR_BUFFER_BIT);

    glBindVertexArray(cat_vao);
    glUseProgram(program);
    glDrawElements(GL_TRIANGLES, 12, GL_UNSIGNED_INT, 0);

    glutSwapBuffers();
    glFlush();
}

void saveScreenShot(void) {
    int currentwidth = glutGet(GLUT_WINDOW_WIDTH);
    int currentheight = glutGet(GLUT_WINDOW_HEIGHT);
    Screenshot imag = Screenshot(currentwidth, currentheight);
    imag.save("cat_shape.png");
}

void keyboard(unsigned char key, int x, int y) {
    switch (key) {
        case 27: // Esc key
            exit(0);
            break;
        case 'h': // Help
            printHelp();
            break;
        case 'o': // Screenshot
            saveScreenShot();
            break;
        default:
            glutPostRedisplay();
            break;
    }
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);

#ifdef __APPLE__
    glutInitDisplayMode(GLUT_3_2_CORE_PROFILE | GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
#else
    glutInitContextVersion(3, 1);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
#endif

    glutInitWindowSize(width, height);
    glutCreateWindow(title);

#ifndef __APPLE__
    glewExperimental = GL_TRUE;
    GLenum err = glewInit();
    if (GLEW_OK != err) {
        std::cerr << "Error: " << glewGetErrorString(err) << std::endl;
    }
#endif

    std::cout << "OpenGL Version: " << glGetString(GL_VERSION) << std::endl;

    initialize();

    glutDisplayFunc(display);
    glutKeyboardFunc(keyboard);

    glutMainLoop();
    return 0;
}