#define GLM_FORCE_RADIANS
#define GLM_ENABLE_EXPERIMENTAL
#include "GeomTriangle.h"

#include <glm/glm.hpp>
#include <glm/gtx/string_cast.hpp>
#include <iostream>

#include "Intersection.h"
#include "Ray.h"

GeomTriangle::GeomTriangle(std::vector<glm::vec3> &vertices, std::vector<glm::vec3> &normals) {
    this->vertices[0] = vertices[0];
    this->vertices[1] = vertices[1];
    this->vertices[2] = vertices[2];

    this->normals[0] = normals[0];
    this->normals[1] = normals[1];
    this->normals[2] = normals[2];
}

std::vector<Intersection> GeomTriangle::intersect(Ray &ray) {
    using namespace glm;
    /**
     * NOTE: Ray is already transformed to the Model coordinate space.
     */

    // vector to store the intersections
    std::vector<Intersection> intersections;

    /**
     * TODO: Implement the Ray intersection with the current geometry
     */

    // Extract triangle vertices
    vec3 v0 = vertices[0];
    vec3 v1 = vertices[1];
    vec3 v2 = vertices[2];

    // Compute triangle edges
    vec3 edge1 = v1 - v0;
    vec3 edge2 = v2 - v0;

    // Compute determinant using cross product
    vec3 h = cross(ray.dir, edge2);
    float det = dot(edge1, h);

    // If determinant is near zero, ray is parallel to triangle (no intersection)
    if (abs(det) < 1e-6) return intersections;

    float inv_det = 1.0f / det;

    // Compute barycentric coordinates
    vec3 s = ray.p0 - v0;
    float u = dot(s, h) * inv_det;
    if (u < 0.0f || u > 1.0f) return intersections;

    vec3 q = cross(s, edge1);
    float v = dot(ray.dir, q) * inv_det;
    if (v < 0.0f || u + v > 1.0f) return intersections;

    // Compute intersection distance t
    float t = dot(edge2, q) * inv_det;
    if (t <= 0) return intersections; // Intersection behind camera

    /**
     * Once you find the intersection, add it to the `intersections` vector.
     *
     * Example:
     * Suppose the ray intersects the current geometry at a point `vec3 point`
     * at a distance `float t`, and the unit normal vector at the intersection
     * point is `vec3 normal`. You would then insert an intersection into the
     * vector as follows:
     *
     * intersections.push_back({t, point, normal, this, nullptr});
     *
     * Note:
     * - Here we pass the Model pointer as `nullptr` because it will be
     *   populated by the Model::intersect() function call.
     * - Only add intersections that are in front of the camera, i.e.,
     *   where t > 0.
     */

    // Compute intersection point
    vec3 point = ray.p0 + t * ray.dir;

    // Compute normal (interpolated if smooth shading, flat otherwise)
    vec3 normal = normalize(cross(edge1, edge2));
    
    /**
     * TODO: Update `intersections`
     */
    intersections.push_back({t, point, normal, this, nullptr});

    return intersections;
}