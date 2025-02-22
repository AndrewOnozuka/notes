#version 330 core

// Inputs to the fragment shader are outputs
// of the same name of the vertex shader
in vec2 canvas_coord; // range [-1,1]x[-1,1]

uniform vec2 center;
uniform float zoom;
uniform int maxiter;

// Output the frag color
out vec4 fragColor;

// HW1: You can define customized functions here,
// e.g. complex multiplications, helper functions
// for colormap etc.

vec2 complexMult(vec2 z1, vec2 z2) {
    return vec2(z1.x * z2.x - z1.y * z2.y, z1.x * z2.y + z1.y * z2.x);
}

void main (void){
    
    vec2 c = center + zoom * canvas_coord;
    // HW1: Your implementation goes here. Compute
    // the value of the Mandelbrot fractal at
    // complex number c.  Then map the value to
    // some color.

    // Initialize z to 0
    vec2 z = vec2(0.0, 0.0);

    // Number of iterations to escape
    int iter = 0;

    // Mandelbrot iteration
    while (iter < maxiter && dot(z, z) <= 4.0) {
        z = complexMult(z, z) + c;
        iter++;
    }

    // Map the number of iterations to a color
    if (iter == maxiter) {
        // Points inside the Mandelbrot set are black
        fragColor = vec4(0.0, 0.0, 0.0, 1.0);
    } else {
        // Use a smooth color gradient for points outside the set
        float t = float(iter) / float(maxiter);
        
        // fragColor = vec4(vec3(1.0 - t), 1.0);            // white to gray
        // fragColor = vec4(1.0, t, t, 1.0);                // red to white
        // fragColor = vec4(t, 0.0, 0.0, 1.0);              // black to red
        // fragColor = vec4(vec3(t), 1.0);                  // black to white
        // fragColor = vec4(t, t * 0.84, t * 0.0, 1.0);     // black to gold
        // fragColor = vec4(0.0, 0.0, t, 1.0);              // black to blue
        // fragColor = vec4(t, 0.0, t, 1.0);                // black to purple
        // fragColor = vec4(vec3(1.0 - t), 1.0);            // white to black
        fragColor = vec4(t * 0.5, 0.0, t, 1.0);             // purple
    }
}
