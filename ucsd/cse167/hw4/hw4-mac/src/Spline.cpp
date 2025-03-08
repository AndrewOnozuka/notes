#include "Spline.h"
#include <iostream>
// Implementation of Spline::Bezier, Spline::BSpline, Spline::Subdiv.
// Each of these functions have input
//    ControlCurve* control,
//    Curve* curve,
//   and some int for resolution.
// Each function should take information from control (specifically, the
// list of point positions control->P ) and add points to the 2nd curve.
// When these functions are called, curve->P is an empty vector<vec2>.
// You may use Curve::addPoint( glm::vec2 position ) to append a new point
// position to curve.

typedef std::vector<glm::vec2> vec2s;

// Some possible helper functions

// Given a list of points and t in [0,1], output Bezier point at t
// Helper function for de Casteljau algorithm
glm::vec2 deCasteljau(vec2s P_in, float t) {
    while (P_in.size() > 1) {
        vec2s newPoints;
        for (size_t i = 0; i < P_in.size() - 1; i++) {
            newPoints.push_back((1 - t) * P_in[i] + t * P_in[i + 1]);
        }
        P_in = newPoints;
    }
    return P_in[0];
}

// Given 4 points and some t in [2,3], output the BSpline point at t
// Compute the B-Spline curve point using De Boor's Algorithm
glm::vec2 BSplineHelper(glm::vec2 P1, glm::vec2 P2, glm::vec2 P3, glm::vec2 P4, float alpha) {
    float alpha2 = alpha * alpha;
    float alpha3 = alpha2 * alpha;

    // B-Spline cubic basis functions (corrected)
    float b0 = (-alpha3 + 3*alpha2 - 3*alpha + 1) / 6.0f;
    float b1 = (3*alpha3 - 6*alpha2 + 4) / 6.0f;
    float b2 = (-3*alpha3 + 3*alpha2 + 3*alpha + 1) / 6.0f;
    float b3 = alpha3 / 6.0f;

    // Compute B-Spline point
    return b0 * P1 + b1 * P2 + b2 * P3 + b3 * P4;
}

// Helper functions for subdivision
// Given n points, find 2*n points that are the result of subdivision.
vec2s SubdivLeft(vec2s P_in) {  // first n points
    vec2s newPoints;
    newPoints.push_back(P_in[0]);
    for (size_t i = 0; i < P_in.size() - 1; i++) {
        newPoints.push_back((3.0f / 4.0f) * P_in[i] + (1.0f / 4.0f) * P_in[i + 1]);
    }
    return newPoints;
}

vec2s SubdivRight(vec2s P_in) { // The (n+1)th to 2n-th points.
    vec2s newPoints;
    for (size_t i = 0; i < P_in.size() - 1; i++) {
        newPoints.push_back((1.0f / 4.0f) * P_in[i] + (3.0f / 4.0f) * P_in[i + 1]);
    }
    newPoints.push_back(P_in.back());
    return newPoints;
}

void Spline::Bezier(ControlCurve *control, Curve *curve, int resolution)
{
    vec2s controlPoints = control->P;
    for (int i = 0; i < resolution + 1; i++)
    {
        // t continuously ranges from 0 to 1
        float t = float(i) / float(resolution);
        // HW4: Your code goes here.
        curve->addPoint(deCasteljau(controlPoints, t));
    }
}
void Spline::BSpline(ControlCurve *control, Curve *curve, int resolution)
{
    int n = control->size();
    if (n >= 4)
    { // We only do BSpline when there are at least 4 control points
        for (int i = 0; i < resolution + 1; i++)
        {
            // t continuously ranges from 1 to n-2
            float t = 1.f + float(n - 3) * float(i) / float(resolution);

            // HW4: Your code goes here
            int k = int(t);  // Integer part of t
            float alpha = t - k;  // Fractional part of t

            // Ensure k is within a safe range
            if (k >= n - 3) k = n - 3;
            if (k - 1 < 0 || k + 2 >= control->P.size()) continue;

            // Compute the B-Spline point
            glm::vec2 p = BSplineHelper(control->P[k - 1], control->P[k], control->P[k + 1], control->P[k + 2], alpha);

            // Fix: Ensure the last computed point is exactly at the last valid curve position
            if (i == resolution) p = BSplineHelper(control->P[n - 4], control->P[n - 3], control->P[n - 2], control->P[n - 1], 1.0f);

            curve->addPoint(p);
        }
    }
}
void Spline::Subdiv(ControlCurve *control, Curve *curve, int subdivLevel)
{
    // HW4: Your code goes here
    // HW4: The result of subdivision should converge to the BSpline curve.
    //      You can design a recursion.  Or you can write for loops that subdivide
    //      the correct set of curve segments at each level.
    vec2s points = control->P;

    for (int i = 0; i < subdivLevel; i++) {
        vec2s midpoints, smoothedPoints, finalPoints;

        // Step 1: Compute midpoints
        for (size_t j = 0; j < points.size() - 1; j++) {
            glm::vec2 midpoint = 0.5f * (points[j] + points[j + 1]);
            midpoints.push_back(midpoint);
        }

        // Step 2: Compute smoothed control points (excluding first and last)
        for (size_t j = 1; j < points.size() - 1; j++) { // Skip first and last
            glm::vec2 smoothed = (1.0f / 8.0f) * points[j - 1] +
                                 (3.0f / 4.0f) * points[j] +
                                 (1.0f / 8.0f) * points[j + 1];
            smoothedPoints.push_back(smoothed);
        }

        // Step 3: Interleave midpoints and smoothed points in correct order
        for (size_t j = 0; j < smoothedPoints.size(); j++) {
            finalPoints.push_back(midpoints[j]);     // Midpoint first
            finalPoints.push_back(smoothedPoints[j]); // Smoothed point next
        }

        // Step 4: Ensure the last midpoint is included
        finalPoints.push_back(midpoints.back());

        // Update points for next iteration
        points = finalPoints;
    }

    // Clear the curve and add points in correct order
    curve->P.clear();
    for (glm::vec2 pt : points) {
        curve->addPoint(pt);
    }
}
