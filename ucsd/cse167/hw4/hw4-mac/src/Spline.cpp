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
// glm::vec2 deCasteljau(vec2s P_in, float t){}

// Given 4 points and some t in [2,3], output the BSpline point at t
/* glm::vec2 BSplineHelper(glm::vec2 P1,
                        glm::vec2 P2,
                        glm::vec2 P3,
                        glm::vec2 P4, float t){}*/

// Given n points, find 2*n points that are the result of subdivision.
// vec2s SubdivLeft(vec2s P_in){}  // first n points
// vec2s SubdivRight(vec2s P_in){} // The (n+1)th to 2n-th points.

void Spline::Bezier(ControlCurve *control, Curve *curve, int resolution)
{

    for (int i = 0; i < resolution + 1; i++)
    {
        // t continuously ranges from 0 to 1
        float t = float(i) / float(resolution);
        // HW4: Your code goes here.
        // curve -> addPoint( ... );
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
            //
        }
    }
}
void Spline::Subdiv(ControlCurve *control, Curve *curve, int subdivLevel)
{
    // HW4: Your code goes here
    // HW4: The result of subdivision should converge to the BSpline curve.
    //      You can design a recursion.  Or you can write for loops that subdivide
    //      the correct set of curve segments at each level.
}
