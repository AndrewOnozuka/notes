///////////////////////////////////////////////////////////////////////////////
// Main Class File:    PointTester.java
// File:               Point.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Bao Hoang
//////////////////////////// 80 columns wide //////////////////////////////////


import java.util.*;

/**
 * A class to represent a Point on a 2D coordinate plane.
 *
 */
public class Point {
    private int x; // The point's x coordinate (as an int)
    private int y; // The point's y coordinate (as an int)
    private String type; // The point's type (e.g. Home, Store, School)

    /**
     * Constructor: creates a point object.
     *
     * @param x    the x coordinate of the point
     * @param y    the y coordinate of the point
     * @param type the location type of the point
     */
    public Point(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Return the x coordinate of the point.
     *
     * @return the x coordinate of the point
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y coordinate of the point.
     *
     * @return the y coordinate of the point
     */
    public int getY() {
        return this.y;
    }

    /**
     * Return the type of the point.
     *
     * @return the type of the point
     */
    public String getType() {
        return this.type;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1, 0, "Blacks beack"); 
        Point p2 = new Point(2, 1, "Home");
        Point p3 = new Point(3, 0, "School"); 
        ArrayList<Point> points = new ArrayList<Point>();

        points.add(p1);
        points.add(p2);
        points.add(p3);
        int i = 0;
        while (i < points.size()) { 
            System.out.println(points.get(i).getX()); 
            i++;
        }
    }
}


