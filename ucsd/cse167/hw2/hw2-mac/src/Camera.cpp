#include "Camera.h"
#include <math.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <cmath>

// HW2: You can add more helper functions if you want!

// Helper function: Rodrigues' rotation formula
glm::mat3 rotation(const float degrees, const glm::vec3 axis) {
    const float radians = glm::radians(degrees); // Convert degrees to radians
    const float cos_theta = cos(radians);
    const float sin_theta = sin(radians);

    glm::mat3 identity(1.0f); // Identity matrix
    glm::mat3 axis_outer = glm::outerProduct(axis, axis); // Outer product
    glm::mat3 axis_cross = glm::mat3(
        0, -axis.z, axis.y,
        axis.z, 0, -axis.x,
        -axis.y, axis.x, 0
    );

    return cos_theta * identity +
           (1 - cos_theta) * axis_outer +
           sin_theta * axis_cross;
}

// Rotate camera right (yaw)
void Camera::rotateRight(const float degrees) {
    glm::vec3 view_dir = eye - target; // Direction from target to eye
    glm::mat3 rot_matrix = rotation(-degrees, up); // Negate angle for right-handed system

    eye = target + rot_matrix * view_dir; // Rotate the eye position
}
// Rotate camera up (pitch)
void Camera::rotateUp(const float degrees) {
    glm::vec3 view_dir = eye - target; // Direction from target to eye
    glm::vec3 right = glm::normalize(glm::cross(up, view_dir)); // Right vector
    glm::mat3 rot_matrix = rotation(degrees, right); // Rotate around the right vector

    eye = target + rot_matrix * view_dir; // Rotate the eye
    up = glm::normalize(rot_matrix * up); // Update and normalize the up vector
}
void Camera::computeMatrices() {
    // Compute the view matrix
    glm::vec3 z_axis = glm::normalize(eye - target); // Forward vector
    glm::vec3 x_axis = glm::normalize(glm::cross(up, z_axis)); // Right vector
    glm::vec3 y_axis = glm::cross(z_axis, x_axis); // New up vector

    view = glm::mat4(1.0f); // Initialize as identity
    view[0][0] = x_axis.x; view[1][0] = x_axis.y; view[2][0] = x_axis.z; view[3][0] = -glm::dot(x_axis, eye);
    view[0][1] = y_axis.x; view[1][1] = y_axis.y; view[2][1] = y_axis.z; view[3][1] = -glm::dot(y_axis, eye);
    view[0][2] = z_axis.x; view[1][2] = z_axis.y; view[2][2] = z_axis.z; view[3][2] = -glm::dot(z_axis, eye);
    view[3][3] = 1.0f;

    // Compute the projection matrix
    float fovy_rad = glm::radians(fovy); // Convert fovy to radians
    float tan_half_fovy = tan(fovy_rad / 2.0f);

    proj = glm::mat4(0.0f); // Initialize as zero
    proj[0][0] = 1.0f / (aspect * tan_half_fovy);
    proj[1][1] = 1.0f / tan_half_fovy;
    proj[2][2] = -(far + near) / (far - near);
    proj[2][3] = -1.0f;
    proj[3][2] = -(2.0f * far * near) / (far - near);
}

void Camera::reset(){
    eye = eye_default;// position of the eye
    target = target_default;  // look at target
    up = up_default;      // up vector
    fovy = fovy_default;  // field of view in degrees
    aspect = aspect_default; // aspect ratio
    near = near_default; // near clipping distance
    far = far_default; // far clipping distance
}