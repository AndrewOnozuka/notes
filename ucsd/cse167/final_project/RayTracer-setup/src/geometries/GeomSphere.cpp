#include "GeomSphere.h"

#include <iostream>
#include <utility>

#include "Intersection.h"
#include "Ray.h"

std::vector<Intersection> GeomSphere::intersect(Ray &ray) {
    /**
     * NOTE: Ray is already transformed to the Model coordinate space.
     */
    using namespace glm;

    // vector to store the intersections
    std::vector<Intersection> intersections;

    /**
     * TODO: Implement the Ray intersection with the current geometry
     */
    // Sphere properties (centered at origin in model space)
    vec3 center = vec3(0.0f);
    float radius = 1.0f;

    // Extract ray properties
    vec3 ro = ray.p0;         // Ray origin
    vec3 rd = normalize(ray.dir); // Ray direction (should already be normalized)

    // Compute quadratic equation coefficients
    vec3 oc = ro - center;
    float a = dot(rd, rd);
    float b = 2.0f * dot(oc, rd);
    float c = dot(oc, oc) - radius * radius;

    // Compute discriminant
    float discriminant = b * b - 4 * a * c;

    // No real intersection if discriminant is negative
    if (discriminant < 0) {
        return intersections;
    }

    // Compute the two possible intersection distances
    float sqrt_disc = sqrt(discriminant);
    float t1 = (-b - sqrt_disc) / (2.0f * a);
    float t2 = (-b + sqrt_disc) / (2.0f * a);

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

    /**
     * TODO: Update `intersections`
     */
    // Only add intersections in front of the camera (t > 0)
    if (t1 > 0) {
        vec3 point = ro + t1 * rd;
        vec3 normal = normalize(point - center);
        intersections.push_back({t1, point, normal, this, nullptr});
    }
    if (t2 > 0) {
        vec3 point = ro + t2 * rd;
        vec3 normal = normalize(point - center);
        intersections.push_back({t2, point, normal, this, nullptr});
    }

    return intersections;
};